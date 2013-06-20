package com.sino.ams.match.model;

import com.sino.base.dto.DTO;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.framework.dto.BaseUserDTO;

public class TFManualMatchModel extends AMSSQLProducer {
    public TFManualMatchModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }
}
