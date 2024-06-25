package org.example.util;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.example.dto.Supplier;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplierReport {
    public static boolean genarateSupplierReport(List<Supplier> supplierList) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/jrxmlFiles/supplierReport.jrxml");
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(supplierList);

            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            JasperExportManager.exportReportToPdfFile(jasperPrint, "supplierReport.pdf");
            openPdf("supplierReport.pdf");
            return true;
        } catch (JRException e) {
            e.printStackTrace();
        }
        return false;
    }
    private static void openPdf(String pdfFilePath) {
        if (Desktop.isDesktopSupported()) {
            try {
                File pdfFile = new File(pdfFilePath);
                if (pdfFile.exists()) {
                    Desktop.getDesktop().open(pdfFile);
                } else {
                    System.err.println("The file " + pdfFilePath + " does not exist.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Desktop is not supported on this platform.");
        }
    }
}
