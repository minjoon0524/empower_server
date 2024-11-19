package com.inhatc.empower.constant;


import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum LeaveType {
    GENERAL, //일반
    HALF_DAY, // 반차
    SICK_LEAVE, // 병가
    CONDOLENCE_SPOUSE, // 조사(배우자)
    CONDOLENCE_PARENT, //조사(부모님)
    CONDOLENCE_SIBLING, // 조사(형제)
    MILITARY_SERVICE; // 예비군



}
