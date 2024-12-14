package ru.itis.tjmoney.controllers.user;

import com.cloudinary.Cloudinary;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import ru.itis.tjmoney.services.ApplicationService;
import ru.itis.tjmoney.services.GroupService;
import ru.itis.tjmoney.services.TransactionService;
import ru.itis.tjmoney.services.UserService;
import ru.itis.tjmoney.util.CloudinaryUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;

@WebServlet("/user/changeAvatar")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024) // 5 MB
public class ChangeAvatarServlet extends HttpServlet {
    private UserService userService;
    private TransactionService transactionService;
    private GroupService groupService;
    private ApplicationService applicationService;
    private final Cloudinary cloudinary = CloudinaryUtil.getInstance();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) getServletContext().getAttribute("userService");
        transactionService = (TransactionService) getServletContext().getAttribute("transactionService");
        groupService = (GroupService) getServletContext().getAttribute("groupService");
        applicationService = (ApplicationService) getServletContext().getAttribute("applicationService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = (Integer) req.getSession().getAttribute("userId");

        req.setAttribute("urlPhoto", userService.getPhotoUrl(userId));
        req.getRequestDispatcher("/templates/users/changeAvatar.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = (Integer) req.getSession().getAttribute("userId");

        String method = req.getParameter("_method");
        if (method != null && method.equalsIgnoreCase("DELETE")) {
            userService.deletePhoto(userId);
            resp.sendRedirect("/user");
            return;
        }

        Part filePart = req.getPart("file");
        if (filePart != null && filePart.getSize() > 0) {
            InputStream fileContent = filePart.getInputStream();
            File tempFile = File.createTempFile("upload_", ".tmp");
            Files.copy(fileContent, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            try {
                Map uploadResult = cloudinary.uploader().upload(tempFile, Map.of());
                String avatarUrl = (String) uploadResult.get("secure_url");

                userService.uploadPhoto(userId, avatarUrl);

                resp.sendRedirect("/user");
            } catch (Exception e) {
                throw new RuntimeException("Error uploading avatar", e);
            } finally {
                tempFile.delete();
            }
        }
    }
}
