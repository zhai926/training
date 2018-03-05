package com.lehui.service;

import com.lehui.entity.FloorModel;

import java.util.List;

/**
 * floor model 逻辑类
 * Created by SunHaiyang on 2017/6/12.
 */
public interface FloorModelService {

    /**
     * 新增
     * @param floorModel
     * @return
     */
    public FloorModel saveFloorModel(FloorModel floorModel);

    /**
     * 通过id删除
     * @param id
     * @return
     */
    public boolean deleteFloorModel(long id);

    /**
     * 更改floor model
     * @param floorModel
     * @return
     */
    public FloorModel updateFloorModel(FloorModel floorModel);

    /**
     * 根据id 查询
     * @param id
     * @return
     */
    public FloorModel findById(long id);


    /**
     * 查找所有
     * @return
     */
    public List<FloorModel> findAll();
}
