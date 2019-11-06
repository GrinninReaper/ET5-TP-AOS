import javax.ws.rs.core.MediaType;

@POST
@Produces(MediaType.TEXT_PLAIN, MediaType.TEXT_HTML)
public String doPOST(@Context HttpHeader, )