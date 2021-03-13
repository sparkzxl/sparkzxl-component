package com.github.sparkzxl.database.base.controller;

import com.github.sparkzxl.database.base.service.SuperCacheService;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.Serializable;

/**
 * description: super 缓存controller
 *
 * @author: zhouxinlei
 * @date: 2020-07-29 10:56:21
 */
public abstract class SuperCacheController<S extends SuperCacheService<Entity>, Id extends Serializable, Entity, SaveDTO, UpdateDTO, QueryDTO>
        extends SuperController<S, Id, Entity, SaveDTO, UpdateDTO, QueryDTO> {

    /**
     * 查询
     *
     * @param id 主键id
     * @return 查询结果
     */
    @Override
    public Entity get(@PathVariable Id id) {
        return baseService.getByIdCache(id);
    }

}
