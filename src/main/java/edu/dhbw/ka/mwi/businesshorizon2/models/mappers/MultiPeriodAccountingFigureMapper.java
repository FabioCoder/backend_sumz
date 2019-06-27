package edu.dhbw.ka.mwi.businesshorizon2.models.mappers;

import java.util.List;

import edu.dhbw.ka.mwi.businesshorizon2.models.daos.MultiPeriodAccountingFigureDao;
import edu.dhbw.ka.mwi.businesshorizon2.models.daos.TimeSeriesItemDao;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.MultiPeriodAccountingFigureRequestDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.MultiPeriodAccountingFigureResponseDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.TimeSeriesItemResponseDto;

public class MultiPeriodAccountingFigureMapper {
    
    public static MultiPeriodAccountingFigureDao mapDtoToDao(MultiPeriodAccountingFigureRequestDto dto) {
        
        if (dto == null) {
            return null;
        }
        
        List<TimeSeriesItemDao> timeSeriesItems = TimeSeriesItemMapper.mapDtoToDao(dto.getTimeSeries());
        String figureName = dto.getFigureName() != null ? dto.getFigureName().name() : null;
        
        
        Integer [] seasonalOrder = {null, null, null, null};
        Integer [] order = {null, null, null};
        
        
        if (dto.getSeasonalOrder() != null) {
        	seasonalOrder = dto.getSeasonalOrder();
        }
        
        if (dto.getOrder() != null) {
        	order = dto.getOrder();
        }
        
        MultiPeriodAccountingFigureDao dao = new MultiPeriodAccountingFigureDao(figureName, dto.getIsHistoric(), timeSeriesItems,
        		order[0], order[1], order[2],
                seasonalOrder[0], seasonalOrder[1], seasonalOrder[2], seasonalOrder[3]);
        
        
        if (dto.getScore() != null) {
            dao.setScore(dto.getScore());
        }
        
        return dao;
    }
    
    public static MultiPeriodAccountingFigureResponseDto mapDaoToDto(MultiPeriodAccountingFigureDao dao) {
        
        if (dao == null) {
            return null;
        }
        
        List<TimeSeriesItemResponseDto> timeSeriesItems = TimeSeriesItemMapper.mapDaoToDto(dao.getTimeSeriesItems());
        
        if (dao.getP() == null) {
            dao.setP(0);
        }
        
        if (dao.getD() == null) {
            dao.setD(0);
        }
        
        if (dao.getQ() == null) {
            dao.setQ(0);
        }
        
        if (dao.getsP() == null) {
            dao.setsP(0);
        }
        
        if (dao.getsD() == null) {
            dao.setsD(0);
        }
        
        if (dao.getsQ() == null) {
            dao.setsQ(0);
        }
        
        if (dao.getsF() == null) {
            dao.setsF(0);
        }
        
        Integer order[] = {dao.getP(), dao.getD(), dao.getQ()};
        Integer seasonalOrder[] = {dao.getsP(), dao.getsD(), dao.getsQ(), dao.getsF()};
        
        MultiPeriodAccountingFigureResponseDto dto = new MultiPeriodAccountingFigureResponseDto(dao.getIsHistoric(), timeSeriesItems, order, seasonalOrder);
        
        if (dao.getScore() != null) {
            dto.setScore(dao.getScore());
        }
        
        return dto;
    }
}
