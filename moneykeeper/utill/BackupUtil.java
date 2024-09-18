
package me.bakumon.moneykeeper.utill;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Locale;

import me.bakumon.moneykeeper.database.AppDatabase;
import me.bakumon.moneykeeper.ui.setting.BackupBean;

public class BackupUtil {
    public static final String BACKUP_DIR = "backup_moneykeeper";
    public static final String AUTO_BACKUP_PREFIX = "MoneyKeeperBackupAuto";
    public static final String USER_BACKUP_PREFIX = "MoneyKeeperBackupUser";
    public static final String SUFFIX = ".db";
    public static final String BACKUP_SUFFIX = "_backup";

    private static boolean backupDB(Context context, String fileName) {
        File backupDir = context.getExternalFilesDir(BACKUP_DIR);
        if (!backupDir.exists() && !backupDir.mkdirs()) {
            return false;
        }
        File backupFile = new File(backupDir, fileName);

        SQLiteDatabase db = context.openOrCreateDatabase(AppDatabase.DB_NAME, Context.MODE_PRIVATE, null);
        try (InputStream inputStream = new FileInputStream(db.getPath());
             OutputStream outputStream = new FileOutputStream(backupFile)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean autoBackup(Context context) {
        String fileName = AUTO_BACKUP_PREFIX + SUFFIX;
        return backupDB(context, fileName);
    }

    public static boolean autoBackupForNecessary(Context context) {
        String fileName = AUTO_BACKUP_PREFIX + SUFFIX;
        File backupDir = context.getExternalFilesDir(BACKUP_DIR);
        File backupFile = new File(backupDir, fileName);

        if (!backupDir.exists() && !backupDir.mkdirs()) {
            return false;
        }

        // 检查是否已经存在备份文件，如果不存在则创建
        if (!backupFile.exists()) {
            return backupDB(context, fileName);
        }

        // 这里可以添加额外的逻辑，例如检查上次备份时间
        // 如果超过了一定的时间间隔，则进行备份
        long lastBackupTime = backupFile.lastModified();
        long currentTime = System.currentTimeMillis();
        long backupInterval = 24 * 60 * 60 * 1000; // 假设备份间隔为24小时

        if ((currentTime - lastBackupTime) > backupInterval) {
            return backupDB(context, fileName);
        }

        return true;
    }

    public static boolean userBackup(Context context) {
        String fileName = USER_BACKUP_PREFIX + SUFFIX;
        return backupDB(context, fileName);
    }

    public static boolean restoreDB(Context context, File restoreFile) {
        SQLiteDatabase db = context.openOrCreateDatabase(AppDatabase.DB_NAME, Context.MODE_PRIVATE, null);
        try (InputStream inputStream = new FileInputStream(restoreFile);
             OutputStream outputStream = new FileOutputStream(db.getPath())) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<BackupBean> getBackupFiles(Context context) {
        File backupDir = context.getExternalFilesDir(BACKUP_DIR);
        List<BackupBean> backupBeans = new ArrayList<>();
        if (backupDir != null && backupDir.isDirectory()) {
            for (File file : backupDir.listFiles()) {
                if (file.getName().endsWith(SUFFIX)) {
                    BackupBean bean = new BackupBean();
                    bean.file = file;
                    bean.name = file.getName();
                    bean.size = humanReadableByteCount(file.length());
                    bean.time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date(file.lastModified()));
                    backupBeans.add(bean);
                }
            }
        }
        return backupBeans;
    }

    private static String humanReadableByteCount(long bytes) {
        int unit = 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        char pre = "KMGTPE".charAt(exp-1);
        return String.format(Locale.getDefault(), "%.1f %cB", bytes / Math.pow(unit, exp), pre);
    }
}