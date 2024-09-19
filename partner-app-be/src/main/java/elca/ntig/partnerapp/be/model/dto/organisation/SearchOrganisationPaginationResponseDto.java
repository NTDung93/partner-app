package elca.ntig.partnerapp.be.model.dto.organisation;

import elca.ntig.partnerapp.be.model.dto.person.PersonResponseDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchOrganisationPaginationResponseDto {
    private int pageNo;
    private int pageSize;
    private int totalPages;
    private long totalRecords;
    private boolean last;
    private List<OrganisationResponseDto> content;
}
