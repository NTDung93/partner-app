==table person==
-- nationality enum--
ALTER TABLE person
ADD CONSTRAINT check_enum_value_nationality
CHECK (nationality IN ('CH', 'DE', 'ES', 'FR', 'GB', 'IT'));

-- sex enum --
ALTER TABLE person
ADD CONSTRAINT check_enum_value_sex
CHECK (sex IN ('M', 'F'));

-- marital status enum --
ALTER TABLE person
ADD CONSTRAINT check_enum_value_marital_status
CHECK (marital_status IN ('SINGLE', 'MARRIED', 'WIDOWED', 'DIVORCED', 'SEPARATED', 'UNKNOWN'));


==table partner==
-- language enum --
ALTER TABLE partner
ADD CONSTRAINT check_enum_value_language
CHECK (language IN ('DE', 'EN', 'FR', 'IT'));

-- status enum --
ALTER TABLE partner
ADD CONSTRAINT check_enum_value_status
CHECK (status IN ('ACTIVE', 'INACTIVE'));

==table organisation==
-- code noga enum --
ALTER TABLE organisation
ADD CONSTRAINT check_enum_value_code_noga
CHECK (code_noga IN (
    '011100',
    '011200',
    '011400',
    '011500',
    '011900',
    '012101',
    '013000',
    '014200',
    '014700',
    '016300',
    '021000',
    '024000',
    '031200',
    '032100',
    '032200',
    '051000',
    '052000',
    '061000',
    '062000',
    '071000'
));

-- legal status enum --
ALTER TABLE organisation
ADD CONSTRAINT check_enum_value_legal_status
CHECK (legal_status IN (
    'S_SIMPLE',
    'S_NOM_COL',
    'S_C_SIMPLE',
    'COMHEREDIT',
    'S_ANON',
    'S_RESP_L',
    'S_COOP',
    'S_C_ACTION',
    'ASSOC',
    'FOUNDATION',
    'CORP_PUB'
));


==table address==
-- address type --
ALTER TABLE address
ADD CONSTRAINT check_enum_value_address_type
CHECK (category IN ('PRINCIP', 'SECOND', 'CORRESP'));

-- canton abbr type --
ALTER TABLE address
ADD CONSTRAINT check_enum_value_canton
CHECK (canton IS NULL OR canton IN (
    'AG',  -- AARGAU
    'AI',  -- APPENZELL_INNER_RHODES
    'AR',  -- APPENZELL_OUTER_RHODES
    'BE',  -- BERN
    'BL',  -- BASEL_COUNTRY
    'BS',  -- BASEL_CITY
    'FR',  -- FRIBOURG
    'GE',  -- GENEVA
    'GL',  -- GLARUS
    'GR',  -- GRISONS
    'JU',  -- JURA
    'LU',  -- LUCERNE
    'NE',  -- NEUCHATEL
    'NW',  -- NIDWALD
    'OW',  -- OBWALD
    'SG',  -- ST_GALL
    'SH',  -- SCHAFFHOUSE
    'SO',  -- SOLOTHURN
    'SZ',  -- SCHWYZ
    'TG',  -- THURGAU
    'TI',  -- TESSIN
    'UR',  -- URI
    'VD',  -- VAUD
    'VS',  -- VALAIS
    'ZG',  -- ZUG
    'ZH'   -- ZURICH
));


-- country type --
ALTER TABLE address
ADD CONSTRAINT check_enum_value_country
CHECK (country IS NULL OR country IN ('CH', 'DE', 'ES', 'FR', 'GB', 'IT'));