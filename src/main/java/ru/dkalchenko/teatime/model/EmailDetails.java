package ru.dkalchenko.teatime.model;

import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class EmailDetails {

    @NonNull
    private String recipient;

    @NonNull
    private String msgBody;

    @NonNull
    private String subject;

    private String attachment;
}
