package elca.ntig.partnerapp.be.model.enums.organisation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CodeNOGA {
    /*
        011100	Cereal cultivation
        011200	Rice cultivation
        011400	Sugar cane cultivation
        011500 	Tobacco cultivation
        011900 	Other non-perennial crops
        012101	Viticulture
        013000	Plant multiplication and production
        014200	Raising other cattle and buffalo
        014700	Poultry farm
        016300 	Primary crop processing
        021000	Silviculture and other forestry activities
        024000 	Logging support services
        031200 	Freshwater fishing
        032100 	Offshore aquaculture
        032200	Freshwater aquaculture
        051000	Coal mining
        052000	Lignite mining
        061000	Crude oil extraction
        062000	Natural gas extraction
        071000	Iron ore mining
     */
    NOGA_011100,
    NOGA_011200,
    NOGA_011400,
    NOGA_011500,
    NOGA_011900,
    NOGA_012101,
    NOGA_013000,
    NOGA_014200,
    NOGA_014700,
    NOGA_016300,
    NOGA_021000,
    NOGA_024000,
    NOGA_031200,
    NOGA_032100,
    NOGA_032200,
    NOGA_051000,
    NOGA_052000,
    NOGA_061000,
    NOGA_062000,
    NOGA_071000
}
