package xyz.e3ndr.consolidate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class CommandEvent<T> {
    private @NonNull String[] args = new String[0];
    private T executor;

}
