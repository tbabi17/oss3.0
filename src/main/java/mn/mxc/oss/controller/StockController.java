package mn.mxc.oss.controller;

import mn.mxc.oss.domain.*;
import mn.mxc.oss.services.StockEndService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
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
public class StockController {
	
	@Autowired
	StockEndService service;

	@RequestMapping(value = "stock/findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable findAll(@RequestParam int page, @RequestParam int size,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		List list = service.findAll(page, size);
		Hashtable pageable = new Hashtable();
		pageable.put("total", service.total());
		pageable.put("data", list);
		return pageable;
	}
	@RequestMapping(value = "stock/findAvailable", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable findAvailable(@RequestParam int page, @RequestParam int size,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		List list = service.findAvailable(page, size);
		Hashtable pageable = new Hashtable();
		pageable.put("total", service.total());
		pageable.put("data", list);
		return pageable;
	}

	@RequestMapping(value = "stock/clear", method = RequestMethod.DELETE)
	public void delete(@RequestParam int warehouseId) {

	}

	@RequestMapping(value = "stock/balanceByUsers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable balanceByUsers(@RequestParam String startDate, @RequestParam String endDate,
									@RequestParam int page, @RequestParam int size,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		List list = service.balanceByUsers(startDate, endDate, page, size);
		Hashtable pageable = new Hashtable();
		pageable.put("total", list.size());
		pageable.put("data", list);
		return pageable;
	}
	@RequestMapping(value = "stock/balanceByUsersExport", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public void balanceByUsersExport(@RequestParam String startDate, @RequestParam String endDate,
									@RequestParam int page, @RequestParam int size,HttpServletResponse response, HttpServletRequest req) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		List<BalanceByUser> list = service.balanceByUsers(startDate, endDate, page, size);
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("logged");
		if(user!=null) {
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition",
					"attachment;filename=balanceByUser "+startDate+" "+endDate+".xlsx");

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Export");
			Object[][] bookData = {};
			int rowCount = 0;

			Row row = sheet.createRow(++rowCount);
			Cell cell = row.createCell(0);
			cell.setCellValue("Борлуулагч");

			cell = row.createCell(1);
			cell.setCellValue("Захиалгын тоо");

			cell = row.createCell(2);
			cell.setCellValue("Захиалгын дүн");
			int i = 0;
			while(i<list.size()){
				row = sheet.createRow(++rowCount);
				String salesman = list.get(i).getUser().getFirstName()+" "+list.get(i).getUser().getLastName();
				double total_order = list.get(i).getQty();
				double amount = list.get(i).getAmount();

				cell = row.createCell(0);
				cell.setCellValue((String) salesman);

				cell = row.createCell(1);
				cell.setCellValue(total_order);

				cell = row.createCell(2);
				cell.setCellValue(amount);

				i++;
			}
			try (FileOutputStream outputStream = new FileOutputStream("e://tmp/balanceByUser "+startDate+" "+endDate+".xlsx")) {
				workbook.write(outputStream);
				File file = new File("e://tmp/balanceByUser "+startDate+" "+endDate+".xlsx");
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

	@RequestMapping(value = "stock/balanceByProducts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable balanceByProducts(@RequestParam String startDate, @RequestParam String endDate, @RequestParam int page, @RequestParam int size,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		List list = service.balanceByProducts(startDate, endDate, page, size);
		Hashtable pageable = new Hashtable();
		pageable.put("total", list.size());
		pageable.put("data", list);
		return pageable;
	}
	@RequestMapping(value = "stock/balanceByProductsExport", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public void balanceByProductsExport(@RequestParam String startDate, @RequestParam String endDate, @RequestParam int page, @RequestParam int size,HttpServletResponse response, HttpServletRequest req) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		List<BalanceByProduct> list = service.balanceByProducts(startDate, endDate, page, size);
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("logged");
		if(user!=null) {
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition",
					"attachment;filename=balanceByProducts "+startDate+" "+endDate+".xlsx");

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Export");
			Object[][] bookData = {};
			int rowCount = 0;

			Row row = sheet.createRow(++rowCount);
			Cell cell = row.createCell(0);
			cell.setCellValue("Бараа");

			cell = row.createCell(1);
			cell.setCellValue("Тоо ширхэг");

			cell = row.createCell(2);
			cell.setCellValue("Нийт дүн");
			int i = 0;
			while(i<list.size()) {
				row = sheet.createRow(++rowCount);
				String pname = list.get(i).getProduct().getName();
				double qty = list.get(i).getQty();
				double amount = list.get(i).getAmount();

				cell = row.createCell(0);
				cell.setCellValue((String) pname);

				cell = row.createCell(1);
				cell.setCellValue(qty);

				cell = row.createCell(2);
				cell.setCellValue(amount);
				i++;
			}
			try (FileOutputStream outputStream = new FileOutputStream("e://tmp/balanceByProducts "+startDate+" "+endDate+".xlsx")) {
				workbook.write(outputStream);
				File file = new File("e://tmp/balanceByProducts "+startDate+" "+endDate+".xlsx");
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

	@RequestMapping(value = "stock/balanceByDay", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable balanceByDay(@RequestParam String startDate, @RequestParam String endDate, @RequestParam int page, @RequestParam int size,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		List list = service.balanceByDay(startDate, endDate, page, size);
		Hashtable pageable = new Hashtable();
		pageable.put("total", list.size());
		pageable.put("data", list);
		return pageable;
	}
	@RequestMapping(value = "stock/balanceByDayExport", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public void balanceByDayExport(@RequestParam String startDate, @RequestParam String endDate, @RequestParam int page, @RequestParam int size,HttpServletResponse response, HttpServletRequest req) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		List<BalanceByDay> list = service.balanceByDay(startDate, endDate, page, size);
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("logged");
		if(user!=null) {
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition",
					"attachment;filename=balanceByDay " + startDate + " " + endDate + ".xlsx");

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Export");
			Object[][] bookData = {};
			int rowCount = 0;

			Row row = sheet.createRow(++rowCount);
			Cell cell = row.createCell(0);
			cell.setCellValue("Өдөр");

			cell = row.createCell(1);
			cell.setCellValue("Ажилласан борлуулагч");

			cell = row.createCell(2);
			cell.setCellValue("Харилцагчийн тоо");

			cell = row.createCell(3);
			cell.setCellValue("Цэгийн тоо");

			cell = row.createCell(4);
			cell.setCellValue("Барааны нэр төрөл");

			cell = row.createCell(5);
			cell.setCellValue("Захиалгын тоо");

			cell = row.createCell(6);
			cell.setCellValue("Захиалгын дүн");
			int i = 0;
			while(i<list.size()){
				row = sheet.createRow(++rowCount);
				String date = list.get(i).getDate();
				double userCount = list.get(i).getUserCount();
				double productCount = list.get(i).getProductCount();
				double customerCount = list.get(i).getCustomerCount();
				double orderCount = list.get(i).getOrderCount();
				double qty = list .get(i).getQty();
				double amount = list.get(i).getAmount();
				cell = row.createCell(0);
				cell.setCellValue(date);

				cell = row.createCell(1);
				cell.setCellValue(userCount);

				cell = row.createCell(2);
				cell.setCellValue(customerCount);

				cell = row.createCell(3);
				cell.setCellValue(orderCount);

				cell = row.createCell(4);
				cell.setCellValue(productCount);

				cell = row.createCell(5);
				cell.setCellValue(qty);

				cell = row.createCell(6);
				cell.setCellValue(amount);
				i++;
			}
			try (FileOutputStream outputStream = new FileOutputStream("e://tmp/balanceByDay "+startDate+" "+endDate+".xlsx")) {
				workbook.write(outputStream);
				File file = new File("e://tmp/balanceByDay "+startDate+" "+endDate+".xlsx");
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

	@RequestMapping(value = "stock/calc", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public int calc(@RequestParam int warehouseId) {
		return service.calc(warehouseId);
	}

	@RequestMapping(value = "stock/balance", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable balance(@RequestParam int warehouseId, @RequestParam String startDate, @RequestParam String endDate, @RequestParam int page, @RequestParam int size,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		List list = service.balance(warehouseId, startDate, endDate, page, size);
		Hashtable pageable = new Hashtable();
		pageable.put("total", service.total());
		pageable.put("data", list);

		return pageable;
	}
}
