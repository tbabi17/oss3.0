package mn.mxc.oss.controller;

import mn.mxc.oss.domain.ReportCustomer;
import mn.mxc.oss.domain.ReportProduct;
import mn.mxc.oss.domain.ReportWeekDay;
import mn.mxc.oss.domain.User;
import mn.mxc.oss.services.ReportService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

@RestController
public class ReportController {
	
	@Autowired
	ReportService service;

	@RequestMapping(value = "report/view", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable report(@RequestParam String report, @RequestParam String startDate, @RequestParam String endDate, @RequestParam int page, @RequestParam int size,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		List list = service.report(report, startDate, endDate, page, size);
		Hashtable pageable = new Hashtable();
		pageable.put("total", service.total(report));
		pageable.put("data", list);

		return pageable;
	}
	@RequestMapping(value = "report/export", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public void reportExport(@RequestParam String report, @RequestParam String startDate, @RequestParam String endDate, @RequestParam int page, @RequestParam int size,HttpServletResponse response, HttpServletRequest req) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("logged");
		if(user!=null) {
			String reportName = report+" " + startDate + " " + endDate;
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition",
					"attachment;filename="+reportName+".xlsx");

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Export");
			Object[][] bookData = {};
			int rowCount = 0;

			Row row = sheet.createRow(rowCount);

			switch (report) {
				case "weekDay": {
					List<ReportWeekDay> list = service.report(report, startDate, endDate, page, size);
					CellStyle hcenter = workbook.createCellStyle();
					hcenter.setAlignment(HorizontalAlignment.CENTER);
					Cell cell = row.createCell(0);
					cell.setCellValue("Борлуулагч");

					cell = row.createCell(1);
					cell.setCellValue("Цэг");

					cell = row.createCell(2);
					cell.setCellStyle(hcenter);
					cell.setCellValue("Даваа");

					cell = row.createCell(5);
					cell.setCellStyle(hcenter);
					cell.setCellValue("Мягмар");

					cell = row.createCell(8);
					cell.setCellStyle(hcenter);
					cell.setCellValue("Лхагва");

					cell = row.createCell(11);
					cell.setCellStyle(hcenter);
					cell.setCellValue("Пүрэв");

					cell = row.createCell(14);
					cell.setCellStyle(hcenter);
					cell.setCellValue("Баасан");

					cell = row.createCell(17);
					cell.setCellStyle(hcenter);
					cell.setCellValue("Бямба");

					cell = row.createCell(20);
					cell.setCellStyle(hcenter);
					cell.setCellValue("Ням");

					cell = row.createCell(23);
					cell.setCellValue("Нийт");
					sheet.addMergedRegion(new CellRangeAddress(0,1,0,0));
					sheet.addMergedRegion(new CellRangeAddress(0,1,1,1));
					sheet.addMergedRegion(new CellRangeAddress(0,0,2,4));
					sheet.addMergedRegion(new CellRangeAddress(0,0,5,7));
					sheet.addMergedRegion(new CellRangeAddress(0, 0,8,10));
					sheet.addMergedRegion(new CellRangeAddress(0,0,11,13));
					sheet.addMergedRegion(new CellRangeAddress(0, 0,14,16));
					sheet.addMergedRegion(new CellRangeAddress(0,0,17,19));
					sheet.addMergedRegion(new CellRangeAddress(0, 0,20,22));
					sheet.addMergedRegion(new CellRangeAddress(0,0,23,25 ));
					row = sheet.createRow(++rowCount);
					//Даваа
					cell = row.createCell(2);
					cell.setCellValue("Цэг");
					cell = row.createCell(3);
					cell.setCellValue("Орсон");
					cell = row.createCell(4);
					cell.setCellValue("Дүн");
					//Мягмар
					cell = row.createCell(5);
					cell.setCellValue("Цэг");
					cell = row.createCell(6);
					cell.setCellValue("Орсон");
					cell = row.createCell(7);
					cell.setCellValue("Дүн");
					//Лхагва
					cell = row.createCell(8);
					cell.setCellValue("Цэг");
					cell = row.createCell(9);
					cell.setCellValue("Орсон");
					cell = row.createCell(10);
					cell.setCellValue("Дүн");
					//Пүрэв
					cell = row.createCell(11);
					cell.setCellValue("Цэг");
					cell = row.createCell(12);
					cell.setCellValue("Орсон");
					cell = row.createCell(13);
					cell.setCellValue("Дүн");
					//Баасан
					cell = row.createCell(14);
					cell.setCellValue("Цэг");
					cell = row.createCell(15);
					cell.setCellValue("Орсон");
					cell = row.createCell(16);
					cell.setCellValue("Дүн");
					//Бямба
					cell = row.createCell(17);
					cell.setCellValue("Цэг");
					cell = row.createCell(18);
					cell.setCellValue("Орсон");
					cell = row.createCell(19);
					cell.setCellValue("Дүн");
					//Ням
					cell = row.createCell(20);
					cell.setCellValue("Цэг");
					cell = row.createCell(21);
					cell.setCellValue("Орсон");
					cell = row.createCell(22);
					cell.setCellValue("Дүн");
					//Нийт
					cell = row.createCell(23);
					cell.setCellValue("Цэг");
					cell = row.createCell(24);
					cell.setCellValue("Орсон");
					cell = row.createCell(25);
					cell.setCellValue("Дүн");
					int i = 0;
					while(i<list.size()){
						row = sheet.createRow(++rowCount);
						String code = list.get(i).getUser().getOwner();
						String salesman = list.get(i).getUser().getFirstName();
						double monMCount = list.get(i).getMonMCount();
						double monECount = list.get(i).getMonECount();
						double monAmt = list.get(i).getMonAmt();
						double tueMCount= list.get(i).getTueMCount();
						double tueECount = list.get(i).getTueECount();
						double tueAmt = list.get(i).getTueAmt();
						double wedMCount = list.get(i).getWedECount();
						double wedECount = list.get(i).getWedECount();
						double wedAmt = list.get(i).getWedAmt();
						double thuMCount = list.get(i).getThuMCount();
						double thuECount = list.get(i).getThuECount();
						double thuAmt = list.get(i).getThuAmt();
						double friMCount = list.get(i).getFriMCount();
						double friECount = list.get(i).getFriECount();
						double friAmt = list.get(i).getFriAmt();
						double satMCount = list.get(i).getSatMCount();
						double satECount = list.get(i).getSatECount();
						double satAmt = list.get(i).getSatAmt();
						double sunMCount = list.get(i).getSunMCount();
						double sunECount = list.get(i).getSunECount();
						double sunAmt = list.get(i).getSunAmt();
						double totalMCount = list.get(i).getTotalMCount();
						double totalECount = list.get(i).getTotalECount();
						double totalAmt = list.get(i).getTotalAmt();
						cell = row.createCell(0);
						cell.setCellValue(code);
						cell = row.createCell(1);
						cell.setCellValue(salesman);
						cell = row.createCell(2);
						cell.setCellValue(monMCount);
						cell = row.createCell(3);
						cell.setCellValue(monECount);
						cell = row.createCell(4);
						cell.setCellValue(monAmt);
						cell = row.createCell(5);
						cell.setCellValue(tueMCount);
						cell = row.createCell(6);
						cell.setCellValue(tueECount);
						cell = row.createCell(7);
						cell.setCellValue(tueAmt);
						cell = row.createCell(8);
						cell.setCellValue(wedMCount);
						cell = row.createCell(9);
						cell.setCellValue(wedECount);
						cell = row.createCell(10);
						cell.setCellValue(wedAmt);
						cell = row.createCell(11);
						cell.setCellValue(thuMCount);
						cell = row.createCell(12);
						cell.setCellValue(thuECount);
						cell = row.createCell(13);
						cell.setCellValue(thuAmt);
						cell = row.createCell(14);
						cell.setCellValue(friMCount);
						cell = row.createCell(15);
						cell.setCellValue(friECount);
						cell = row.createCell(16);
						cell.setCellValue(friAmt);
						cell = row.createCell(17);
						cell.setCellValue(satMCount);
						cell = row.createCell(18);
						cell.setCellValue(satECount);
						cell = row.createCell(19);
						cell.setCellValue(satAmt);
						cell = row.createCell(20);
						cell.setCellValue(sunMCount);
						cell = row.createCell(21);
						cell.setCellValue(sunECount);
						cell = row.createCell(22);
						cell.setCellValue(sunAmt);
						cell = row.createCell(23);
						cell.setCellValue(totalMCount);
						cell = row.createCell(24);
						cell.setCellValue(totalECount);
						cell = row.createCell(25);
						cell.setCellValue(totalAmt);
						i++;
					}
				}
				break;
				case "productReport": {
					List<ReportProduct> list = service.report(report, startDate, endDate, page, size);

				}
				break;
				case "customerReport": {
					List<ReportCustomer> list = service.report(report, startDate, endDate, page, size);

				}
				break;
				default: {
					System.out.println("Report default action....");
				}
				break;
			}
			try (FileOutputStream outputStream = new FileOutputStream("e://tmp/"+reportName+".xlsx")) {
				workbook.write(outputStream);
				File file = new File("e://tmp/"+reportName+".xlsx");
				FileInputStream fileIn = new FileInputStream(file);
				ServletOutputStream out = response.getOutputStream();

				byte[] outputByte = new byte[4096];
				//copy binary contect to output stream
				while (fileIn.read(outputByte, 0, 4096) != -1) {
					out.write(outputByte, 0, 4096);
				}
				fileIn.close();
				out.flush();
				out.close();
				System.out.println("Export process complete...");
			} catch (IOException e) {
				System.out.println("Export process failed due exception...");
				System.out.println("exception message: " + e.getMessage());
			}
		}
	}
}
