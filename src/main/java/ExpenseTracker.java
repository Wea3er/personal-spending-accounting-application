import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExpenseTracker {

    private Map<String, Double> expenses;
    private Map<String, Double> categoryExpenses;

    private Map<String, CategoryInfo> categoryInfoHashMap;

    public ExpenseTracker() {
        categoryInfoHashMap = new HashMap<>();

    }

    public void recordExpense(double amount, String category) {
        if(!categoryInfoHashMap.containsKey(category)){
            categoryInfoHashMap.put(category,new CategoryInfo(category));
        }
        var categoryInfo = categoryInfoHashMap.get(category);
        categoryInfo.addCost(getCurrentDate(), amount);
    }
    private Double calculateTotalExpenses(){
        var total = 0.0;

        for(var k : categoryInfoHashMap.keySet()){
            total += categoryInfoHashMap.get(k).getAllCost();
        }
        return total;
    }
    public void showTotalExpenses() {
        var total = calculateTotalExpenses();
        System.out.println("Общие расходы:" + total);
    }

    public void showCategoryExpenses(){
        for(var category : categoryInfoHashMap.values()){
            System.out.println("Расходы по категориям '" + category.getName() + "':" + category.getAllCost());
        }
    }
    public void showCategoryExpenses(String category) {
        if(!categoryInfoHashMap.containsKey(category)) return;
        var categoryInfo = categoryInfoHashMap.get(category);
        System.out.println("Расходы по категориям '" + category + "':" + categoryInfo.getAllCost());
    }

    public void showPercentageDistribution() {
        var total = calculateTotalExpenses();


        for(var c : categoryInfoHashMap.keySet()){
            double percentage = (categoryInfoHashMap.get(c).getAllCost() / total) * 100;
            System.out.println(c + ": " + String.format("%.2f%%", percentage));
        }
    }
    @SuppressWarnings("deprecation")
    public void saveExpensesToExcel(String filePath) {
        try (var workbook = new HSSFWorkbook()) {
                var sheet = workbook.createSheet("Расходы");
                int rowNumber = 0;
                var headerRow = sheet.createRow(rowNumber++);
                headerRow.createCell(0).setCellValue("Дата");
                headerRow.createCell(1).setCellValue("Сумма");
                headerRow.createCell(2).setCellValue("Категория");
                for (var category : categoryInfoHashMap.values()) {
                        for (var entry : category.getCosts().entrySet()) {
                                var row = sheet.createRow(rowNumber++);
                                row.createCell(0).setCellValue(entry.getKey());
                                row.createCell(1).setCellValue(entry.getValue());
                                row.createCell(2).setCellValue(category.getName());
                                // You may add more columns as needed, e.g., category
                            }
                    }
                try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                        workbook.write(fileOut);
                        System.out.println("Расходы успешно сохранены в файле Excel.");
                    }
            } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showStats(String category){
        if(!categoryInfoHashMap.containsKey(category)) return;
        var categoryInfo = categoryInfoHashMap.get(category);
        var costs = categoryInfo.getCosts();
        for(var k : costs.keySet()){
            System.out.println(k + " : " + costs.get(k));
        }
    }
    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(new Date());
    }



}