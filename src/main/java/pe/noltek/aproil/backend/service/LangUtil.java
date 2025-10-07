package pe.noltek.aproil.backend.service;

public class LangUtil {
    private LangUtil() {
    }
    static boolean has(String s) { return s != null && !s.isBlank(); }
    static String pick(String lang, String es, String en) {
        if ("en".equalsIgnoreCase(lang)) return has(en) ? en : es;
        return has(es) ? es : en;
    }
    static String norm(String lang) { return (lang == null || lang.isBlank()) ? "es" : lang; }
}
