package mn.mxc.oss.controller;
import com.sun.deploy.util.StringUtils;
import mn.mxc.oss.dao.ProductDao;
import mn.mxc.oss.domain.Customer;
import mn.mxc.oss.domain.Prices;
import mn.mxc.oss.domain.Product;
import mn.mxc.oss.domain.User;
import mn.mxc.oss.services.CustomerService;
import mn.mxc.oss.services.PricesService;
import mn.mxc.oss.services.ProductService;
import mn.mxc.oss.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by P67 on 3/28/2017.
 */
@RestController
public class ApiController {
    private static String UPLOADED_FOLDER = "C://tmp//";
    @Autowired(required=true)
    private ProductService service;
    @Qualifier(value="service")
    public void setProductService(ProductService ps){
        this.service = ps;
    }
    @Autowired(required=true)
    private UserService uservice;
    @Qualifier(value="uservice")
    public void setUserService(UserService us){
        this.uservice = us;
    }
    @Autowired(required=true)
    private PricesService prservice;
    @Qualifier(value="prservice")
    public void setUserService(PricesService prs){
        this.prservice = prs;
    }
    @Autowired(required=true)
    private CustomerService cservice;
    @Qualifier(value="cservice")
    public void setCustomerService(CustomerService cs){
        this.cservice = cs;
    }
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "import/xls_upload")
    public Hashtable upload(@RequestParam("file") MultipartFile file, HttpServletRequest req) throws IOException {
        HttpSession session = req.getSession();
        String userid = session.getAttribute("owner").toString();
        byte[] bytes;
        Hashtable pageable = new Hashtable();
        if (!file.isEmpty()) {
            bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            String ext = file.getOriginalFilename().split("\\.")[1];
            if(ext.equals("xls") || ext.equals("xlsx")) {
                Files.write(path, bytes);
                System.out.println(UPLOADED_FOLDER + file.getOriginalFilename());
                //excel read
                Hashtable res = read_import_excel(UPLOADED_FOLDER + file.getOriginalFilename(),userid);
                pageable.putAll(res);
            }else{
                pageable.put("status", false);
                pageable.put("msg", "Зөвхөн эксел файл оруулна уу?");
            }
        }else{
            pageable.put("status", false);
            pageable.put("msg", "Эксел файл оруулна уу?");
        }
        return pageable;
    }
    public Hashtable read_import_excel(String filepath,String userid){
        Hashtable pageable = new Hashtable();
        try
        {
            //FileInputStream f = new FileInputStream(new File(UPLOADED_FOLDER + file.getOriginalFilename()));
            FileInputStream f = new FileInputStream(new File(filepath));

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(f);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
            System.out.println("Niit mornii too: "+sheet.getLastRowNum());
            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            int i = 0;
            int l  = 0;
            String name = "";
            String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            while (rowIterator.hasNext())
            {

                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();
                if(i==0){
                    if(row.getCell(0)==null){
                        name = "";
                    }else{
                        name = row.getCell(0).getStringCellValue();
                    }
                    System.out.println("Import name: "+row.getCell(0));
                    switch(name){
                        case "Storage":{

                            pageable.put("status", true);
                            pageable.put("msg","формат "+name);
                        } break;
                        case "Products":{
                            pageable.put("status", true);
                            pageable.put("msg","Өгөгдлийг амжилттай орууллаа. ");

                        } break;
                        default:{
                            if(name == null){
                                System.out.println("Import file name is null");
                                pageable.put("status", false);
                                pageable.put("msg","Эксел хүснэгтийн формат буруу байна!!!");
                            }
                        }
                    }
                }
                if(i>=10){
                    System.out.println("Medeelel oruulj baina.. "+l);
                    if(name.equals("Products")){
                        ProductService ps = new ProductService();
                        Cell cell = row.getCell(0);
                        //cell.setCellType(cell.CELL_TYPE_STRING);
                        String code = "";
                        String pname ="";
                        String brand = "";
                        String type = "";
                        double size = 1;
                        double discount = 0;
                        if(row.getCell(0) != null)
                            code = Double.toString(row.getCell(0).getNumericCellValue());
                        if(row.getCell(3) != null)
                            size = row.getCell(3).getNumericCellValue();
                        if(row.getCell(5) != null)
                            discount = row.getCell(5).getNumericCellValue();
                        if(row.getCell(1) != null)
                            pname = row.getCell(1).getStringCellValue();
                        if(row.getCell(2) != null)
                            brand = row.getCell(2).getStringCellValue();
                        if(row.getCell(4) != null)
                            type = row.getCell(4).getStringCellValue();

                        Product product = new Product();
                        //product.setId(0);
                        product.setCode(code);
                        product.setName(pname);
                        product.setBrand(brand);
                        product.setSize(size);
                        product.setType(type);
                        product.setCreatedDate(datetime);
                        product.setStatus("active");
                        product.setImg("product.png");
                        product.setDiscount(discount);
                        service.save(product);
                        System.out.println(code+" | "+pname+" | "+brand+" | "+size+" | "+type+" | "+datetime+" | "+discount);
                    }else
                    if(name.equals("Users")){
                        String owner = "";
                        String password = "";
                        String phone ="";
                        String fname = "";
                        String lname ="";
                        double mon = 0;
                        double tue = 0;
                        double wed = 0;
                        double thu = 0;
                        double fri = 0;
                        double sat = 0;
                        double sun = 0;
                        String userType = "";
                        row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                        if(row.getCell(0) != null)
                            owner = row.getCell(0).getStringCellValue();
                        if(row.getCell(1) != null)
                            password = row.getCell(1).getStringCellValue();
                        if(row.getCell(2) != null)
                            phone = row.getCell(2).getStringCellValue();
                        if(row.getCell(3) != null)
                            fname = row.getCell(3).getStringCellValue();
                        if(row.getCell(4) != null)
                            lname = row.getCell(4).getStringCellValue();
                        if(row.getCell(5) != null)
                            mon = row.getCell(5).getNumericCellValue();
                        if(row.getCell(6) != null)
                            tue = row.getCell(6).getNumericCellValue();
                        if(row.getCell(7) != null)
                            wed = row.getCell(7).getNumericCellValue();
                        if(row.getCell(8) != null)
                            thu = row.getCell(8).getNumericCellValue();
                        if(row.getCell(9) != null)
                            fri = row.getCell(9).getNumericCellValue();
                        if(row.getCell(10) != null)
                            sat = row.getCell(10).getNumericCellValue();
                        if(row.getCell(11) != null)
                            sun = row.getCell(11).getNumericCellValue();
                        if(row.getCell(12) != null)
                            userType = row.getCell(12).getStringCellValue();
                        User user = new User();
                        user.setOwner(owner);
                        user.setPassword(password);
                        user.setPhone(phone);
                        user.setFirstName(fname);
                        user.setLastName(lname);
                        user.setMon((int)mon);
                        user.setTue((int)tue);
                        user.setWed((int)wed);
                        user.setThu((int)thu);
                        user.setFri((int)fri);
                        user.setSat((int)sat);
                        user.setSun((int)sun);
                        user.setUserType(userType);
                        user.setCreatedDate(datetime);
                        user.setUser_image("user.png");
                        user.setStatus("success");
                        uservice.save(user);
                    }else
                    if(name.equals("Prices")) {
                        double pid = 0;
                        double pricetagid = 0;
                        double price = 0;
                        if(row.getCell(0) != null)
                            pid = row.getCell(0).getNumericCellValue();
                        if(row.getCell(1) != null)
                            pricetagid = row.getCell(1).getNumericCellValue();
                        if(row.getCell(2) != null)
                            price = row.getCell(2).getNumericCellValue();
                        Prices prices = new Prices();
                        prices.setProductId((int)pid);
                        prices.setPriceTagId((int)pricetagid);
                        prices.setPrice(price);
                        prservice.save(prices);
                    }else
                    if(name.equals("Customers")) {
                        String cname = "undefined";
                        String cphone = "";
                        String address = "";
                        double price = 0;
                        double route = 0;
                        double lat = 0;
                        double lng = 0;
                        if(row.getCell(1).getCellType()!=Cell.CELL_TYPE_BLANK){
                            row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                        }
                        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                        if(row.getCell(0).getCellType()!=Cell.CELL_TYPE_BLANK) {
                            row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                        }
                        if(row.getCell(0) != null)
                            cname = row.getCell(0).getStringCellValue();
                        if(row.getCell(1) != null)
                            cphone = row.getCell(1).getStringCellValue();
                        if(row.getCell(2) != null && row.getCell(2).getCellType()!=Cell.CELL_TYPE_BLANK)
                            address = row.getCell(2).getStringCellValue();
                        if(row.getCell(3) != null)
                            price = row.getCell(3).getNumericCellValue();
                        if(row.getCell(4) != null)
                            route = row.getCell(4).getNumericCellValue();
                        if(row.getCell(5) != null)
                            lat = row.getCell(5).getNumericCellValue();
                        if(row.getCell(6) != null)
                            lng = row.getCell(6).getNumericCellValue();
                        Customer customer = new Customer();
                        customer.setName(cname);
                        customer.setPhone(cphone);
                        customer.setAddress(address);
                        customer.setPrice((int)price);
                        customer.setRoute((int)route);
                        customer.setCreatedDate(datetime);
                        customer.setLat(lat);
                        customer.setLng(lng);
                        customer.setUserId(Integer.parseInt(userid));
                        cservice.save(customer);
                    }

                    l++;
                }
                i++;
            }
            pageable.put("total",l);
            f.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return pageable;
    }
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "export/data")
    public void export_excel(@RequestParam String name,HttpServletResponse response, HttpServletRequest req){
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("logged");
        if(user!=null) {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + name + ".xlsx");

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Export");
            Object[][] bookData = {};
            int rowCount = 0;
            switch (name) {
                case "Products": {
                    List<Product> product = service.findAll(1, 3000);
                    Row row = sheet.createRow(++rowCount);
                    Cell cell = row.createCell(0);
                    cell.setCellValue("Код");

                    cell = row.createCell(1);
                    cell.setCellValue("Нэр");

                    cell = row.createCell(2);
                    cell.setCellValue("Бренд");

                    cell = row.createCell(3);
                    cell.setCellValue("Хэмжээ");

                    cell = row.createCell(4);
                    cell.setCellValue("Төрөл");

                    cell = row.createCell(5);
                    cell.setCellValue("Хямдралын хувь");

                    cell = row.createCell(6);
                    cell.setCellValue("Үүсгэсэн огноо");
                    int i = 0;
                    while (i < product.size()) {
                        row = sheet.createRow(++rowCount);
                        String code = product.get(i).getCode();
                        String brand = product.get(i).getBrand();
                        double size = product.get(i).getSize();
                        String type = product.get(i).getType();
                        double discount = product.get(i).getDiscount();
                        String createdat = product.get(i).getCreatedDate();
                        String pname = product.get(i).getName();
                        product.get(i).getName();

                        cell = row.createCell(0);
                        cell.setCellValue((String) code);

                        cell = row.createCell(1);
                        cell.setCellValue((String) pname);

                        cell = row.createCell(2);
                        cell.setCellValue((String) brand);

                        cell = row.createCell(3);
                        cell.setCellValue((double) size);

                        cell = row.createCell(4);
                        cell.setCellValue((String) type);

                        cell = row.createCell(5);
                        cell.setCellValue((double) discount);

                        cell = row.createCell(6);
                        cell.setCellValue((String) createdat);
                        i++;
                    }
                    System.out.println("Niit baraanii too: " + product.size());
                }
                break;
                case "Users": {
                    Row row = sheet.createRow(++rowCount);
                    Cell cell = row.createCell(0);
                    cell.setCellValue("Нэр");

                    cell = row.createCell(1);
                    cell.setCellValue("Утас");

                    cell = row.createCell(2);
                    cell.setCellValue("Овог");

                    cell = row.createCell(3);
                    cell.setCellValue("Нэр");

                    cell = row.createCell(4);
                    cell.setCellValue("Даваа");

                    cell = row.createCell(5);
                    cell.setCellValue("Мягмар");

                    cell = row.createCell(6);
                    cell.setCellValue("Лхагва");

                    cell = row.createCell(7);
                    cell.setCellValue("Пүрэв");

                    cell = row.createCell(8);
                    cell.setCellValue("Баасан");

                    cell = row.createCell(9);
                    cell.setCellValue("Бямба");

                    cell = row.createCell(10);
                    cell.setCellValue("Ням");

                    cell = row.createCell(11);
                    cell.setCellValue("Хэрэглэгчийн төрөл");

                    cell = row.createCell(12);
                    cell.setCellValue("Үүсгэсэн огноо");
                    List<User> users = uservice.findAll(1, 100);
                    int i = 0;
                    while (i < users.size()) {
                        row = sheet.createRow(++rowCount);
                        String owner = users.get(i).getOwner();
                        String phone = users.get(i).getPhone();
                        String fname = users.get(i).getFirstName();
                        String lname = users.get(i).getLastName();
                        double mon = users.get(i).getMon();
                        double tue = users.get(i).getTue();
                        double wed = users.get(i).getWed();
                        double thu = users.get(i).getThu();
                        double fri = users.get(i).getFri();
                        double sat = users.get(i).getSat();
                        double sun = users.get(i).getSun();
                        String type = users.get(i).getUserType();
                        String createdat = users.get(i).getCreatedDate();
                        cell = row.createCell(0);
                        cell.setCellValue((String) owner);

                        cell = row.createCell(1);
                        cell.setCellValue((String) phone);

                        cell = row.createCell(2);
                        cell.setCellValue((String) fname);

                        cell = row.createCell(3);
                        cell.setCellValue((String) lname);

                        cell = row.createCell(4);
                        cell.setCellValue(mon);

                        cell = row.createCell(5);
                        cell.setCellValue(tue);

                        cell = row.createCell(6);
                        cell.setCellValue(wed);

                        cell = row.createCell(7);
                        cell.setCellValue(thu);

                        cell = row.createCell(8);
                        cell.setCellValue(fri);

                        cell = row.createCell(9);
                        cell.setCellValue(sat);

                        cell = row.createCell(10);
                        cell.setCellValue(sun);

                        cell = row.createCell(11);
                        cell.setCellValue(type);

                        cell = row.createCell(12);
                        cell.setCellValue(createdat);
                        i++;
                    }
                    System.out.println("Niit Hereglegchid too: " + users.size());
                }
                break;
                case "Customers": {
                    Row row = sheet.createRow(++rowCount);
                    Cell cell = row.createCell(0);
                    cell.setCellValue("Нэр");

                    cell = row.createCell(1);
                    cell.setCellValue("Утас");

                    cell = row.createCell(2);
                    cell.setCellValue("Хаяг");

                    cell = row.createCell(3);
                    cell.setCellValue("Үнийн төрөл");

                    cell = row.createCell(4);
                    cell.setCellValue("Чиглэл");

                    cell = row.createCell(5);
                    cell.setCellValue("Уртраг");

                    cell = row.createCell(6);
                    cell.setCellValue("Өргөрөг");

                    cell = row.createCell(7);
                    cell.setCellValue("Бүртгэсэн хэрэглэгч");

                    cell = row.createCell(8);
                    cell.setCellValue("Бүртгэсэн огноо");
                    List<Customer> customer = cservice.findAll(1, 5000);
                    int i = 0;
                    while (i < customer.size()) {
                        row = sheet.createRow(++rowCount);
                        String cname = customer.get(i).getName();
                        String phone = customer.get(i).getPhone();
                        String address = customer.get(i).getAddress();
                        double price = customer.get(i).getPrice();
                        double route = customer.get(i).getRoute();
                        double userid = customer.get(i).getUserId();
                        double lat = customer.get(i).getLat();
                        double lng = customer.get(i).getLng();
                        String createdat = customer.get(i).getCreatedDate();
                        cell = row.createCell(0);
                        cell.setCellValue(cname);

                        cell = row.createCell(1);
                        cell.setCellValue(phone);

                        cell = row.createCell(2);
                        cell.setCellValue(address);

                        cell = row.createCell(3);
                        cell.setCellValue((int) price);

                        cell = row.createCell(4);
                        cell.setCellValue((int) route);

                        cell = row.createCell(5);
                        cell.setCellValue(lat);

                        cell = row.createCell(6);
                        cell.setCellValue(lng);

                        cell = row.createCell(7);
                        cell.setCellValue(userid);

                        cell = row.createCell(8);
                        cell.setCellValue(createdat);
                        i++;
                    }
                    System.out.println("Niit Hariltsagchiin too: " + customer.size());

                } break;
                default: {
                }
                break;
            }
            try (FileOutputStream outputStream = new FileOutputStream("e://" + name + ".xlsx")) {
                workbook.write(outputStream);
                File file = new File("e://" + name + ".xlsx");
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
