package com.lylbp.project.controller.test;


import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lylbp.core.entity.ResResult;
import com.lylbp.common.utils.ResResultUtil;
import com.lylbp.manger.redis.service.RedisService;
import com.lylbp.project.service.PermissionService;
import com.lylbp.project.vo.PermissionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author alex
 */
@Slf4j
@Controller
@RequestMapping("/test")
public class TestController {
    @Resource
    private RedisService redisService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private PermissionService permissionService;

    @GetMapping("/play")
    @ResponseBody
    public ModelAndView play() {
        return new ModelAndView("pages/index", new HashMap<>());
    }

    @GetMapping("/redisSave")
    @ResponseBody
    public ResResult<Boolean> redisSave() {
        List<PermissionVO> permissionVOList = permissionService.getPermissionVOListByParams(null, new HashMap<>(1));
        String permissionVOJsonStr = JSON.toJSONString(permissionVOList, SerializerFeature.WriteNullStringAsEmpty);

        int num = 1;
        long start;
        long end;

        //SessionCallback方式
        start = DateUtil.date().getTime();
        redisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public <K, V> Object execute(RedisOperations<K, V> redisOperations) throws DataAccessException {
                for (int i = 0; i < num; i++) {
                    stringRedisTemplate.opsForValue().set(
                            "A_" + i,
                            permissionVOJsonStr,
                            30 * 60,
                            TimeUnit.SECONDS);
                }
                return null;
            }
        });
        end = DateUtil.date().getTime();
        System.out.println("A消耗======" + (end - start));

        //RedisCallback方式
        start = DateUtil.date().getTime();
        redisTemplate.executePipelined((RedisCallback<Object>) redisConnection -> {
            redisConnection.openPipeline();
            for (int i = 0; i < num; i++) {
                redisConnection.stringCommands().set(
                        ("B_:" + i).getBytes(),
                        permissionVOJsonStr.getBytes(),
                        Expiration.from(30 * 60, TimeUnit.SECONDS),
                        RedisStringCommands.SetOption.UPSERT);
            }
            return null;
        });
        end = DateUtil.date().getTime();
        System.out.println("B消耗======" + (end - start));

        //直接循环
        start = DateUtil.date().getTime();
        for (int i = 0; i < num; i++) {
            redisService.strSet("C_" + i, permissionVOJsonStr, 30 * 60L);
        }
        end = DateUtil.date().getTime();
        System.out.println("C消耗======" + (end - start));
        List<PermissionVO> page = ListUtil.page(1, 1, permissionVOList);
        return ResResultUtil.success(true);
    }

    @GetMapping("/redisSave2")
    @ResponseBody
    public ResResult<Boolean> redisSave2() {
        //准备300w条数据开始
        List<PermissionVO> list = permissionService.getPermissionVOListByParams(null, new HashMap<>(1));
        List<PermissionVO> newList = new ArrayList<>(20);
        for (int i = 0; i < 10000; i++) {
            newList.addAll(list);
        }

        List<PermissionVO> permissionVOList = new ArrayList<>(20);
        for (int i = 0; i < 10; i++) {
            permissionVOList.addAll(newList);
        }
        //准备300w条数据结束

        //数据分组
        List<List<PermissionVO>> groupLists = groupList(permissionVOList, 100000);

        long start;
        long end;

//        //1SessionCallback方式
//        start = DateUtil.date().getTime();
//        redisTemplate.executePipelined(new SessionCallback<Object>() {
//            @Override
//            public <K, V> Object execute(RedisOperations<K, V> redisOperations) throws DataAccessException {
//                for (List<PermissionVO> voList : groupLists) {
//                    redisTemplate.opsForList().leftPush("A", voList);
//                    redisTemplate.expire("A", 30 * 60, TimeUnit.SECONDS);
//                }
//
//                return null;
//            }
//        });
//        end = DateUtil.date().getTime();
//        System.out.println("A消耗======" + (end - start));
//
//        //2RedisCallback方式
//        start = DateUtil.date().getTime();
//        redisTemplate.executePipelined((RedisCallback<Object>) redisConnection -> {
//            redisConnection.openPipeline();
//            for (List<PermissionVO> voList : groupLists) {
//                redisConnection.listCommands().rPush(
//                        ("B").getBytes(), new Jackson2JsonRedisSerializer<>(Object.class).serialize(voList));
//            }
//            return null;
//        });
//        end = DateUtil.date().getTime();
//        System.out.println("B消耗======" + (end - start));

        //3直接存
        start = DateUtil.date().getTime();
        redisService.lRightSetAll("C", (List) newList, 30 * 60L);
        end = DateUtil.date().getTime();
        System.out.println("C消耗======" + (end - start));

        //4直接存
        start = DateUtil.date().getTime();
        redisService.lRightSet("D", newList, 30 * 60L);
//        for (List<PermissionVO> voList : groupLists) {
//            ArrayList<PermissionVO> permissionVOS = new ArrayList<>(voList);
//            redisService.lRightSet("C2", permissionVOS, 30 * 60L);
//        }
        end = DateUtil.date().getTime();
        System.out.println("D消耗======" + (end - start));


        return ResResultUtil.success(true);
    }

    @GetMapping("/redisSave3")
    @ResponseBody
    public ResResult<Boolean> redisSave3() {
        //准备300w条数据开始
        List<PermissionVO> list = permissionService.getPermissionVOListByParams(null, new HashMap<>(1));
        List<PermissionVO> newList = new ArrayList<>(20);
        for (int i = 0; i < 10000; i++) {
            newList.addAll(list);
        }

        List<PermissionVO> permissionVOList = new ArrayList<>(20);
        for (int i = 0; i < 6; i++) {
            permissionVOList.addAll(newList);
        }
        //准备300w条数据结束

        //数据分组
        List<List<PermissionVO>> groupLists = groupList(permissionVOList, 10000);

        long start;
        long end;

        //1:SessionCallback方式
        start = DateUtil.date().getTime();
        redisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public <K, V> Object execute(RedisOperations<K, V> redisOperations) throws DataAccessException {
                for (List<PermissionVO> voList : groupLists) {
                    ArrayList<PermissionVO> permissionVOS = new ArrayList<>(voList);
                    redisTemplate.opsForList().rightPushAll("A", (List) permissionVOS);
                    redisTemplate.expire("A", 30 * 60, TimeUnit.SECONDS);
                }

                return null;
            }
        });
        end = DateUtil.date().getTime();
        System.out.println("A消耗======" + (end - start));

        //2:RedisCallback方式
        start = DateUtil.date().getTime();
        redisTemplate.executePipelined((RedisCallback<Object>) redisConnection -> {
            redisConnection.openPipeline();
            for (List<PermissionVO> voList : groupLists) {
                ArrayList<PermissionVO> permissionVOS = new ArrayList<>(voList);
                redisTemplate.opsForList().rightPushAll("B", (List) permissionVOS);
                redisTemplate.expire("B", 30 * 60, TimeUnit.SECONDS);
            }
            return null;
        });
        end = DateUtil.date().getTime();
        System.out.println("B消耗======" + (end - start));


        //3:不利用管道-分组后存
        start = DateUtil.date().getTime();
        for (List<PermissionVO> voList : groupLists) {
            ArrayList<PermissionVO> permissionVOS = new ArrayList<>(voList);
            redisService.lRightSetAll("C", (List) permissionVOS, 30 * 60L);
        }
        end = DateUtil.date().getTime();
        System.out.println("C消耗======" + (end - start));


        return ResResultUtil.success(true);
    }

    @GetMapping("/get")
    @ResponseBody
    public ResResult<PermissionVO> get(@RequestParam String key) {
        List<PermissionVO> list = (List<PermissionVO>) (List) redisService.lGetRange(key, 0L, 0L);
        return ResResultUtil.success(list.get(0));
    }

    @GetMapping("/get2")
    @ResponseBody
    public ResResult<PermissionVO> get2(@RequestParam String key) {
        List<List<PermissionVO>> list = (List<List<PermissionVO>>) (List) redisService.lGetRange(key, 0L, 0L);
        List<PermissionVO> list1 = list.get(0);
        return ResResultUtil.success(list1.get(0));
    }

    @GetMapping("/redisDelete")
    @ResponseBody
    public ResResult<Boolean> redisDelete() {
        redisService.delAllKey();
        return ResResultUtil.success(true);
    }


    public static <T> List<List<T>> groupList(List<T> list, int capacity) {
        List<List<T>> listGroup = new ArrayList<>();
        int listSize = list.size();
        int start = 0;
        if (capacity == 0 || listSize == 0) {
            listGroup.add(list);
            return listGroup;
        }

        while (start < listSize) {
            int toIndex = start + capacity;
            if (toIndex > listSize) {
                toIndex = listSize;
            }

            List<T> group = list.subList(start, toIndex);
            listGroup.add(group);
            start += capacity;
        }

        return listGroup;
    }

}
