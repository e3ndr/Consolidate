package xyz.e3ndr.consolidate;

import org.jetbrains.annotations.Nullable;

import lombok.NonNull;

public interface PermissionChecker<T> {

    public boolean check(@Nullable T executor, @NonNull String permission);

}
