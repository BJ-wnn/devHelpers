package com.github.nan.io;

import org.junit.jupiter.api.Test;

class CosmicContentGeneratorImplTest {

    // cosmic 文档路径
    private final String cosmicFilePath = "/Users/nanan/Desktop/20231130-cosmix/修订/定稿/需求编号084：深度分析云数据库账号管理等功能开发/附件5：需求编号084：深度分析云数据库账号管理等功能开发-COSMIC功能拆分表.xlsx";
    private int columnToReadIndex = 8;
    CosmicContentGenerator cosmicContentGenerator = new CosmicContentGeneratorImpl();


    private final String requirementFilePath = "/Users/nanan/Desktop/test/cosmic_file/021_req.txt";  //需求文档

    @Test
    void generateRequirementContent() {
        cosmicContentGenerator.generateRequirementContent(cosmicFilePath,8,9,requirementFilePath);
    }

    // 单元测试测试项内容文档
    private final String unitTestContentFilePath = "/Users/nanan/Desktop/test/cosmic_file/084_unitContent.txt";
    @Test
    void generateUnitTestContent() {
        cosmicContentGenerator.generateUnitTestContent(cosmicFilePath,columnToReadIndex,unitTestContentFilePath);
    }

    // 单元测试测试 结果表格
    private final String unitTestResultTableFilePath = "/Users/nanan/Desktop/test/cosmic_file/084_unitTestResultTable.csv";
    @Test
    void generateUnitTestResultTable() {
        cosmicContentGenerator.generateUnitTestResultTable(cosmicFilePath,columnToReadIndex,unitTestResultTableFilePath);
    }


    // 联调测试 内容文档
    private final String integrationTestContentFilePath = "/Users/nanan/Desktop/test/cosmic_file/084_integrationTestContent.csv";

    @Test
    void generateIntegrationTestContent() {
        cosmicContentGenerator.generateIntegrationTestContent(cosmicFilePath,columnToReadIndex,integrationTestContentFilePath);
    }

    // 联调测试 表格信息
    private final String  integrationTestResultTableFilePath = "/Users/nanan/Desktop/test/cosmic_file/084_integrationTestResultTable.xlsx";
    @Test
    void generateIntegrationTestResultTable() {
        cosmicContentGenerator.generateIntegrationTestResultTable(cosmicFilePath,columnToReadIndex,integrationTestResultTableFilePath);
    }


}