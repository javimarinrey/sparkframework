package responseTransformer;

import spark.ResponseTransformer;

public class ToCapsTranformer implements ResponseTransformer {
    @Override
    public String render(Object o) throws Exception {
        return o.toString().toUpperCase();
    }
}
