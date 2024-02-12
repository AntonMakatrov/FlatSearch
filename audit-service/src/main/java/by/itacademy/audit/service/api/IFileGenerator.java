package by.itacademy.audit.service.api;

import by.itacademy.audit.core.entity.Audit;

import java.util.List;

public interface IFileGenerator {

    void generateFile(List<Audit> audits, String filename) throws Exception;
}
