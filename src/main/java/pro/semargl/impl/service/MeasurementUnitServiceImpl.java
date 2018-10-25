package pro.semargl.impl.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import pro.semargl.api.dao.MeasurementUnitDao;
import pro.semargl.api.service.MeasurementUnitService;
import pro.semargl.model.MeasurementUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service("measurementUnitService")
public class MeasurementUnitServiceImpl implements MeasurementUnitService {
    private static final Logger LOGGER = Logger.getLogger(MeasurementUnitServiceImpl.class);
    private MeasurementUnitDao measurementUnitDao;

    public MeasurementUnitServiceImpl(MeasurementUnitDao measurementUnitDao) {
        this.measurementUnitDao = measurementUnitDao;
    }

    @Override
    public List<MeasurementUnit> findAll() {
        LOGGER.debug("call findAll()");
        return measurementUnitDao.findAll();
    }

    @Override
    public long save(MeasurementUnit measurementUnit) {
        LOGGER.debug("call save(" + measurementUnit + ")");
        return measurementUnitDao.save(measurementUnit);
    }

    @Override
    public void saveAll(List<MeasurementUnit> entityList) {
        LOGGER.debug("call saveAll(" + entityList + ")");
        List<MeasurementUnit> measurementUnitListDB = this.findAll();
        measurementUnitDao.saveAll(mergeData(measurementUnitListDB, entityList));
    }

    private List<MeasurementUnit> mergeData(List<MeasurementUnit> measurementUnitListDB, List<MeasurementUnit> entityList) {
        LOGGER.debug("call mergeData(" + measurementUnitListDB + "," + entityList + ")");
        List<MeasurementUnit> resultList = new ArrayList<>();
        List<MeasurementUnit> entityListCopy = new ArrayList<>();
        resultList.addAll(measurementUnitListDB);
        entityListCopy.addAll(entityList);
        //add new values from entityList to resultList
        for (int i = 0; i < entityListCopy.size(); i++) {
            MeasurementUnit measurementUnit = entityListCopy.get(i);
            if(!resultList.contains(measurementUnit)){
                resultList.add(measurementUnit);
            }
        }

        //update exist objects if name is same
//        Map<String, MeasurementUnit> measurementUnitMap = resultList
//                .stream().collect(Collectors.toMap(MeasurementUnit::getName, Function.identity()));
//        entityListCopy.forEach(measurementUnit -> {
//            String measurementUnitName = measurementUnit.getName();
//            if(measurementUnitMap.containsKey(measurementUnitName)){
//                MeasurementUnit updatableMeasurementUnit = measurementUnitMap.get(measurementUnitName);
//                updatableMeasurementUnit.setName(measurementUnit.getName());
//            }
//        });
//        resultList.clear();
//        resultList.addAll(measurementUnitMap.values());
        return resultList;
    }
}
