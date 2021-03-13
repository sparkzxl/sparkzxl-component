package com.github.sparkzxl.database.dto;

import lombok.Data;

import java.util.List;

/**
 * description: 删除入参
 *
 * @author zhouxinlei
 * @date 2020-05-24 13:21:12
 */
@Data
public class DeleteDTO<ID> {

    private List<ID> ids;

}
