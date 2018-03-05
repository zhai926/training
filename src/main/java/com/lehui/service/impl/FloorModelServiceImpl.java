package com.lehui.service.impl;

import com.lehui.Repository.FloorModelRepository;
import com.lehui.entity.FloorModel;
import com.lehui.service.FloorModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by SunHaiyang on 2017/6/12.
 */
@Service
@Transactional
public class FloorModelServiceImpl implements FloorModelService {

    @Autowired
    FloorModelRepository floorModelRepository;

    @Override
    public FloorModel saveFloorModel(FloorModel floorModel) {
        return floorModelRepository.save(floorModel);
    }

    @Override
    public boolean deleteFloorModel(long id) {
        boolean flag = true;
        try {
            floorModelRepository.delete(id);
        }catch (Exception e){
            flag = false;
        }
        return flag;
    }

    @Override
    public FloorModel updateFloorModel(FloorModel floorModel) {
        return floorModelRepository.save(floorModel);
    }

    @Override
    public FloorModel findById(long id) {
        return floorModelRepository.findOne(id);
    }

    @Override
    public List<FloorModel> findAll() {
        return floorModelRepository.findAll();
    }
}
