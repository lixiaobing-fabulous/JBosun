package com.lxb.expr.parse.parse;

import com.lxb.error.Error;
import com.lxb.expr.parse.node.Node;
import lombok.Data;

import java.util.List;
import java.util.Map;

public interface Tags {

    TagsAndError apply(List<Node> nodes);

    @Data
    class TagsAndError {
        public Map<String, Object> tags;
        public Error error;
    }
}
