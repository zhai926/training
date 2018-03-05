package com.lehui.Repository;

import com.lehui.entity.FloorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * floorModel 持久层
 * Created by SunHaiyang on 2017/6/12.
 */
@Repository
public interface FloorModelRepository extends JpaRepository<FloorModel,Long> {


}
