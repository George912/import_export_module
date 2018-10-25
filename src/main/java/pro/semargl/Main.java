package pro.semargl;

import org.springframework.context.support.GenericXmlApplicationContext;
import pro.semargl.api.ipt.ImportService;

public class Main {
    private static final String CONTEXT_CONFIG_PATH = "classpath:config/spring/context.xml";

    public static void main(String[] args) {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load(CONTEXT_CONFIG_PATH);
        ctx.refresh();
        ImportService importService = ctx.getBean("importService", ImportService.class);
        importService.startWatching();
    }
}
