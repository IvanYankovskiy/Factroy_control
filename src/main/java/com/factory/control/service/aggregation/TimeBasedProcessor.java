package com.factory.control.service.aggregation;

import java.util.List;

public interface TimeBasedProcessor<I, O> {

    List<O> reduce(List<I> input);

}
