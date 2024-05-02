package palaczjustyna.library.email.domain;

public record EmailToSendDTO (
        String from,
        String to,
        String subject,
        String body){

}

