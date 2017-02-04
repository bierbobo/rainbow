package com.bierbobo.rainbow.service;

import com.bierbobo.rainbow.domain.entity.Entity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lifubo on 2016/11/28.
 */
@Service
public class X2Service implements BaseService<Entity,Entity,String> {
    
    @Override
    public List<Entity> queryList(Entity entity) {
        return null;
    }

    @Override
    public Entity query(Entity entity) {
        return null;
    }

    @Override
    public Entity queryById(String s) {
        return null;
    }

    @Override
    public boolean insert(Entity entity) {
        return false;
    }

    @Override
    public boolean update(Entity entity) {
        return false;
    }

    @Override
    public boolean delete(Entity entity) {
        return false;
    }
}
