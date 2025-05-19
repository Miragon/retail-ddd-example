import {OmitKeyof, QueryClient} from "@tanstack/react-query";
import {createSyncStoragePersister} from "@tanstack/query-sync-storage-persister";
import {PersistQueryClientOptions} from "@tanstack/react-query-persist-client";

export const queryClient = new QueryClient({
    defaultOptions: {
        queries: {
            gcTime: 1000 * 60 * 60 * 24,
        },
    },
})

export const persistOptions: OmitKeyof<PersistQueryClientOptions, "queryClient"> = {
    persister: createSyncStoragePersister({storage: window.localStorage}),
}