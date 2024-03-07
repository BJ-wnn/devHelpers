package com.github.nan.dataprocess.service.impl;

import com.github.nan.dataprocess.constant.Province;
import com.github.nan.dataprocess.req.DataProcessReqVO;
import com.github.nan.dataprocess.service.CommandLineParser;
import org.apache.commons.lang3.ObjectUtils;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;


/**
 * @author NanNan Wang
 * @date 2023/12/20
 */
public class CommandLineParserImpl implements CommandLineParser {

    @Override
    public DataProcessReqVO convert(String[] args) {
        DataProcessReqVO reqVO = new DataProcessReqVO();
        CmdLineParser parser = new CmdLineParser(reqVO);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
//            log.error("程序传入的参数不正确，请检查入参 -processId 、 -period 、 -provinceId ",e);
            throw new RuntimeException(e);
        }

        // 检查参数是否合法
        if(!ObjectUtils.isEmpty(reqVO.getProvinceId()) && !Province.isValidProvinceCode(reqVO.getProvinceId())) {
//            log.error("程序传入的省份不合法 -processId [your_args]");
            throw new IllegalArgumentException("程序传入的省份不合法!");
        }

        // 省份如果没传入，那么默认是全国（业务需求）
        if(ObjectUtils.isEmpty(reqVO.getProvinceId())) {
            reqVO.setProvinceId(Province.NON.getCode());
        }

        return reqVO;
    }
}
