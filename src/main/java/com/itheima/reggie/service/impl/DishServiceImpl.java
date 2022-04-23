package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.DishFlavor;
import com.itheima.reggie.mapper.DishMapper;
import com.itheima.reggie.service.DishFlavorService;
import com.itheima.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;
    /**
     * 新增菜品的同时保存对应的口味数据
     * @param dishDto
     */
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        this.save(dishDto);
        Long dishId = dishDto.getId();

        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors= flavors.stream().peek((item)-> item.setDishId(dishId)).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);

    }


    public DishDto getByIdWithFlavor(Long id) {
        Dish dish = this.getById(id);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        LambdaQueryWrapper<DishFlavor> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);

        dishDto.setFlavors(flavors);
        return dishDto;
    }



    @Transactional
    public void updateWithFlavor(DishDto dishDto) {

         this.updateById(dishDto);

         LambdaQueryWrapper<DishFlavor> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
         dishFlavorService.remove(queryWrapper);
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors= flavors.stream().peek((item)-> item.setDishId(dishDto.getId())).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);

    }


    public void editStatus(Long id,Integer status) {
        Dish dish=this.getById(id);
        dish.setStatus(status);
    }
}