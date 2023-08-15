package vn.id.pmt.spring.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Component
@RequiredArgsConstructor
public class MappingUtil {
    private final ModelMapper modelMapper;

    /**
     * Map source to target class
     *
     * @param <T>         the type parameter
     * @param source      the source
     * @param targetClass the target class
     * @return the target class
     */
    public <T> T map(Object source, Class<T> targetClass) {
        return modelMapper.map(source, targetClass);
    }

    /**
     * Map list source class to target class.
     *
     * @param <S>         the type parameter source
     * @param <T>         the type parameter target
     * @param source      the source
     * @param targetClass the target class
     * @return the list of target class
     */
    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

    /**
     * Map iterable source class to target class.
     *
     * @param <S>         the type parameter source
     * @param <T>         the type parameter target
     * @param source      the source
     * @param targetClass the target class
     * @return the iterable of target class
     */
    public <S, T> Iterable<T> mapIterable(Streamable<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }
}

