package org.example.util;


import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.example.dto.Employee;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeReport {
    public static boolean genarateEmployeeReport(List<Employee> employeeList) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/jrxmlFiles/employeeReport.jrxml");
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employeeList);

            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            JasperExportManager.exportReportToPdfFile(jasperPrint, "employeeReport.pdf");
            return openPdf("employeeReport.pdf");
        } catch (JRException e) {
            e.printStackTrace();
        }
        return false;
    }
    private static boolean openPdf(String pdfFilePath) {
        if (Desktop.isDesktopSupported()) {
            try {
                File pdfFile = new File(pdfFilePath);
                if (pdfFile.exists()) {
                    Desktop.getDesktop().open(pdfFile);
                    return true;
                } else {
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return false;
        }
        return false;
    }
}

