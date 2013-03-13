package com.johnnyangel.myblackbook.db;

import com.antlersoft.android.db.*;

@TableInterface(ImplementingClassName="AbstractConnectionBean",TableName="CONNECTION_BEAN")
interface IConnectionBean {
        @FieldAccessor
        long get_Id();
        @FieldAccessor
        String getNickname();
        @FieldAccessor
        String getAddress();
        @FieldAccessor
        int getPort();
        @FieldAccessor
        String getPassword();
        @FieldAccessor
        String getColorModel();
        @FieldAccessor
        boolean getForceFull();
        @FieldAccessor
        String getRepeaterId();
}


