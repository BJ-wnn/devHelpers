package com.github.nan.dataprocess.req;

import lombok.Data;
import org.kohsuke.args4j.Option;

/**
 * @author NanNan Wang
 * @date 2023/12/20
 */
@Data
public class DataProcessReqVO {


    @Option(required = true,name = "-processId",usage = "接口编码")
    private String processId;

    @Option(required = true,name = "-period",usage = "帐期，全量和增量传入的各式不同")
    private String period;

    @Option(name = "-provinceId", usage = "省份编码，不传入的情况下默认是全国")
    private String provinceId;
}
