package com.quizapp.util;

public class HtmlUtil {

    public static String escape(String input) {
        if (input == null) return "";
        return input.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
    }

    public static String page(String title, String bodyContent) {
        return "<!DOCTYPE html>\n" +
               "<html lang=\"en\">\n" +
               "<head>\n" +
               "<meta charset=\"UTF-8\">\n" +
               "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
               "<title>" + escape(title) + "</title>\n" +
               "<style>" + CSS + "</style>\n" +
               "</head>\n" +
               "<body>\n" +
               "<div class=\"wrapper\">\n" +
               bodyContent +
               "\n</div>\n" +
               "</body>\n" +
               "</html>";
    }

    private static final String CSS =
        "* { box-sizing: border-box; margin: 0; padding: 0; }" +
        "body { font-family: 'Segoe UI', Arial, sans-serif; background: linear-gradient(135deg, #6a11cb 0%, #2575fc 100%); min-height: 100vh; display: flex; align-items: flex-start; justify-content: center; padding: 40px 20px; }" +
        ".wrapper { width: 100%; max-width: 640px; }" +
        ".card { background: #fff; border-radius: 14px; padding: 32px; box-shadow: 0 20px 50px rgba(0,0,0,0.25); }" +
        "h1 { font-size: 26px; margin-bottom: 8px; color: #222; }" +
        "p.subtitle { color: #666; margin-bottom: 24px; }" +
        "input[type=text] { width: 100%; padding: 12px 14px; border: 2px solid #e0e0e0; border-radius: 8px; font-size: 15px; margin-bottom: 20px; }" +
        "input[type=text]:focus { outline: none; border-color: #6a11cb; }" +
        "button, .btn { background: linear-gradient(135deg, #6a11cb 0%, #2575fc 100%); color: #fff; border: none; padding: 13px 22px; border-radius: 8px; font-size: 15px; font-weight: 600; cursor: pointer; text-decoration: none; display: inline-block; }" +
        "button:hover, .btn:hover { opacity: 0.92; }" +
        ".question { margin-bottom: 22px; padding-bottom: 18px; border-bottom: 1px solid #eee; }" +
        ".question:last-child { border-bottom: none; }" +
        ".question p.q-text { font-weight: 600; margin-bottom: 10px; color: #222; }" +
        ".option { display: block; padding: 10px 12px; border: 2px solid #e0e0e0; border-radius: 8px; margin-bottom: 8px; cursor: pointer; transition: 0.15s; }" +
        ".option:hover { border-color: #2575fc; background: #f5f8ff; }" +
        ".option input { margin-right: 10px; }" +
        ".score-badge { text-align: center; margin: 20px 0; }" +
        ".score-badge .num { font-size: 52px; font-weight: 700; color: #6a11cb; }" +
        ".score-badge .label { color: #666; font-size: 14px; }" +
        "table { width: 100%; border-collapse: collapse; margin-top: 10px; }" +
        "th, td { text-align: left; padding: 10px 8px; border-bottom: 1px solid #eee; }" +
        "th { color: #666; font-size: 13px; text-transform: uppercase; }" +
        ".rank-1 { color: #d4af37; font-weight: 700; }" +
        ".rank-2 { color: #a8a8a8; font-weight: 700; }" +
        ".rank-3 { color: #cd7f32; font-weight: 700; }" +
        ".actions { margin-top: 22px; display: flex; gap: 12px; flex-wrap: wrap; }" +
        ".correct { color: #1a8917; font-weight: 600; }" +
        ".incorrect { color: #d92626; font-weight: 600; }";
}
