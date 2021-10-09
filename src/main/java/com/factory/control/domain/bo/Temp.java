package com.factory.control.domain.bo;

import java.time.temporal.Temporal;

public interface Temp<T extends Temporal> {
    T getTime();
}
