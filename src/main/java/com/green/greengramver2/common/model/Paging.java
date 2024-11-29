package com.green.greengramver2.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Paging {
    private final static int DEFAULT_PAGE_SIZE = 20;
    @Schema(example = "1", description = "Selected Page")
    private int page;
    @Schema(example = "20", description = "item count per page")
    private int size;
    @JsonIgnore
    private int sIdx;

    public Paging(Integer page, Integer size) {
        this.page = (page == null || page < 1) ? 1 : page;
        this.size = (size == null || size <= 0) ? DEFAULT_PAGE_SIZE : size;

        this.sIdx = (this.page -1)*this.size;
        // 여기서 this.page 와 this.size로 해주지 않으면 가장 위의 Integer 값으로 사용해서
        // 위에서 값을 따로 지정해둔 것이 정상적으로 작동하지 않을 수 있음
    }
}
