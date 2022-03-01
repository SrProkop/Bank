package ru.t1.bank.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.t1.bank.dto.CurrencyDTO;
import ru.t1.bank.models.Currency;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CurrencyMapper {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "code", source = "code"),
            @Mapping(target = "name", source = "name")
    })
    public abstract CurrencyDTO toCurrencyDTO(Currency currency);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "code", source = "code"),
            @Mapping(target = "name", source = "name")
    })
    public abstract Currency toCurrency(CurrencyDTO currencyDTO);

    public abstract List<CurrencyDTO> toCurrencyDTO(Collection<Currency> currencies);

    public abstract List<Currency> toCurrency(Collection<CurrencyDTO> currencyDTOS);

}
