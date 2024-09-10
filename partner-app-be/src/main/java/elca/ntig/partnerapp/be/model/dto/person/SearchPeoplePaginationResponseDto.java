package elca.ntig.partnerapp.be.model.dto.person;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchPeoplePaginationResponseDto {
    private int pageNo;
    private int pageSize;
    private int totalPages;
    private long totalRecords;
    private boolean last;
    private List<PersonResponseDto> content;
}
