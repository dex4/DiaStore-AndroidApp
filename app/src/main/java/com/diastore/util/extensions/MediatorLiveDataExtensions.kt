package com.diastore.util.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

fun <T, R, X> MediatorLiveData<X>.combineNonNull(
    source1: LiveData<T>,
    source2: LiveData<R>,
    merger: (T, R) -> X
) {
    addSource(source1) { source ->
        value = merger(
            source,
            source2.value ?: return@addSource
        )
    }
    addSource(source2) { source ->
        value = merger(
            source1.value ?: return@addSource,
            source
        )
    }
}

fun <T, R, L, X> MediatorLiveData<X>.combineNonNull(
    source1: LiveData<T>,
    source2: LiveData<R>,
    source3: LiveData<L>,
    merger: (T, R, L) -> X
) {
    addSource(source1) { source ->
        value = merger(
            source,
            source2.value ?: return@addSource,
            source3.value ?: return@addSource
        )
    }
    addSource(source2) { source ->
        value = merger(
            source1.value ?: return@addSource,
            source,
            source3.value ?: return@addSource
        )
    }
    addSource(source3) { source ->
        value = merger(
            source1.value ?: return@addSource,
            source2.value ?: return@addSource,
            source
        )
    }
}

fun <T, R, L, M, X> MediatorLiveData<X>.combineNonNull(
    source1: LiveData<T>,
    source2: LiveData<R>,
    source3: LiveData<L>,
    source4: LiveData<M>,
    merger: (T, R, L, M) -> X
) {
    addSource(source1) { source ->
        value = merger(
            source,
            source2.value ?: return@addSource,
            source3.value ?: return@addSource,
            source4.value ?: return@addSource
        )
    }
    addSource(source2) { source ->
        value = merger(
            source1.value ?: return@addSource,
            source,
            source3.value ?: return@addSource,
            source4.value ?: return@addSource
        )
    }
    addSource(source3) { source ->
        value = merger(
            source1.value ?: return@addSource,
            source2.value ?: return@addSource,
            source,
            source4.value ?: return@addSource
        )
    }
    addSource(source4) { source ->
        value = merger(
            source1.value ?: return@addSource,
            source2.value ?: return@addSource,
            source3.value ?: return@addSource,
            source
        )
    }
}

fun <T, R, L, M, N, X> MediatorLiveData<X>.combineNonNull(
    source1: LiveData<T>,
    source2: LiveData<R>,
    source3: LiveData<L>,
    source4: LiveData<M>,
    source5: LiveData<N>,
    merger: (T, R, L, M, N) -> X
) {
    addSource(source1) { source ->
        value = merger(
            source,
            source2.value ?: return@addSource,
            source3.value ?: return@addSource,
            source4.value ?: return@addSource,
            source5.value ?: return@addSource
        )
    }
    addSource(source2) { source ->
        value = merger(
            source1.value ?: return@addSource,
            source,
            source3.value ?: return@addSource,
            source4.value ?: return@addSource,
            source5.value ?: return@addSource
        )
    }
    addSource(source3) { source ->
        value = merger(
            source1.value ?: return@addSource,
            source2.value ?: return@addSource,
            source,
            source4.value ?: return@addSource,
            source5.value ?: return@addSource
        )
    }
    addSource(source4) { source ->
        value = merger(
            source1.value ?: return@addSource,
            source2.value ?: return@addSource,
            source3.value ?: return@addSource,
            source,
            source5.value ?: return@addSource
        )
    }
    addSource(source5) { source ->
        value = merger(
            source1.value ?: return@addSource,
            source2.value ?: return@addSource,
            source3.value ?: return@addSource,
            source4.value ?: return@addSource,
            source
        )
    }
}

fun <T, R, L, M, N, O, P, Q, X> MediatorLiveData<X>.combineNonNull(
    source1: LiveData<T>,
    source2: LiveData<R>,
    source3: LiveData<L>,
    source4: LiveData<M>,
    source5: LiveData<N>,
    source6: LiveData<O>,
    source7: LiveData<P>,
    source8: LiveData<Q>,
    merger: (T, R, L, M, N, O, P, Q) -> X
) {
    addSource(source1) { source ->
        value = merger(
            source,
            source2.value ?: return@addSource,
            source3.value ?: return@addSource,
            source4.value ?: return@addSource,
            source5.value ?: return@addSource,
            source6.value ?: return@addSource,
            source7.value ?: return@addSource,
            source8.value ?: return@addSource
        )
    }
    addSource(source2) { source ->
        value = merger(
            source1.value ?: return@addSource,
            source,
            source3.value ?: return@addSource,
            source4.value ?: return@addSource,
            source5.value ?: return@addSource,
            source6.value ?: return@addSource,
            source7.value ?: return@addSource,
            source8.value ?: return@addSource
        )
    }
    addSource(source3) { source ->
        value = merger(
            source1.value ?: return@addSource,
            source2.value ?: return@addSource,
            source,
            source4.value ?: return@addSource,
            source5.value ?: return@addSource,
            source6.value ?: return@addSource,
            source7.value ?: return@addSource,
            source8.value ?: return@addSource
        )
    }
    addSource(source4) { source ->
        value = merger(
            source1.value ?: return@addSource,
            source2.value ?: return@addSource,
            source3.value ?: return@addSource,
            source,
            source5.value ?: return@addSource,
            source6.value ?: return@addSource,
            source7.value ?: return@addSource,
            source8.value ?: return@addSource
        )
    }
    addSource(source5) { source ->
        value = merger(
            source1.value ?: return@addSource,
            source2.value ?: return@addSource,
            source3.value ?: return@addSource,
            source4.value ?: return@addSource,
            source,
            source6.value ?: return@addSource,
            source7.value ?: return@addSource,
            source8.value ?: return@addSource
        )
    }

    addSource(source6) { source ->
        value = merger(
            source1.value ?: return@addSource,
            source2.value ?: return@addSource,
            source3.value ?: return@addSource,
            source4.value ?: return@addSource,
            source5.value ?: return@addSource,
            source,
            source7.value ?: return@addSource,
            source8.value ?: return@addSource
        )
    }

    addSource(source7) { source ->
        value = merger(
            source1.value ?: return@addSource,
            source2.value ?: return@addSource,
            source3.value ?: return@addSource,
            source4.value ?: return@addSource,
            source5.value ?: return@addSource,
            source6.value ?: return@addSource,
            source,
            source8.value ?: return@addSource
        )
    }

    addSource(source8) { source ->
        value = merger(
            source1.value ?: return@addSource,
            source2.value ?: return@addSource,
            source3.value ?: return@addSource,
            source4.value ?: return@addSource,
            source5.value ?: return@addSource,
            source6.value ?: return@addSource,
            source7.value ?: return@addSource,
            source
        )
    }
}

fun <T, R, L, M, N, O, P, Q, S, X> MediatorLiveData<X>.combineNonNull(
    source1: LiveData<T>,
    source2: LiveData<R>,
    source3: LiveData<L>,
    source4: LiveData<M>,
    source5: LiveData<N>,
    source6: LiveData<O>,
    source7: LiveData<P>,
    source8: LiveData<Q>,
    source9: LiveData<S>,
    merger: (T, R, L, M, N, O, P, Q, S) -> X
) {
    addSource(source1) { source ->
        value = merger(
            source,
            source2.value ?: return@addSource,
            source3.value ?: return@addSource,
            source4.value ?: return@addSource,
            source5.value ?: return@addSource,
            source6.value ?: return@addSource,
            source7.value ?: return@addSource,
            source8.value ?: return@addSource,
            source9.value ?: return@addSource
        )
    }
    addSource(source2) { source ->
        value = merger(
            source1.value ?: return@addSource,
            source,
            source3.value ?: return@addSource,
            source4.value ?: return@addSource,
            source5.value ?: return@addSource,
            source6.value ?: return@addSource,
            source7.value ?: return@addSource,
            source8.value ?: return@addSource,
            source9.value ?: return@addSource
        )
    }
    addSource(source3) { source ->
        value = merger(
            source1.value ?: return@addSource,
            source2.value ?: return@addSource,
            source,
            source4.value ?: return@addSource,
            source5.value ?: return@addSource,
            source6.value ?: return@addSource,
            source7.value ?: return@addSource,
            source8.value ?: return@addSource,
            source9.value ?: return@addSource
        )
    }
    addSource(source4) { source ->
        value = merger(
            source1.value ?: return@addSource,
            source2.value ?: return@addSource,
            source3.value ?: return@addSource,
            source,
            source5.value ?: return@addSource,
            source6.value ?: return@addSource,
            source7.value ?: return@addSource,
            source8.value ?: return@addSource,
            source9.value ?: return@addSource
        )
    }
    addSource(source5) { source ->
        value = merger(
            source1.value ?: return@addSource,
            source2.value ?: return@addSource,
            source3.value ?: return@addSource,
            source4.value ?: return@addSource,
            source,
            source6.value ?: return@addSource,
            source7.value ?: return@addSource,
            source8.value ?: return@addSource,
            source9.value ?: return@addSource
        )
    }

    addSource(source6) { source ->
        value = merger(
            source1.value ?: return@addSource,
            source2.value ?: return@addSource,
            source3.value ?: return@addSource,
            source4.value ?: return@addSource,
            source5.value ?: return@addSource,
            source,
            source7.value ?: return@addSource,
            source8.value ?: return@addSource,
            source9.value ?: return@addSource
        )
    }

    addSource(source7) { source ->
        value = merger(
            source1.value ?: return@addSource,
            source2.value ?: return@addSource,
            source3.value ?: return@addSource,
            source4.value ?: return@addSource,
            source5.value ?: return@addSource,
            source6.value ?: return@addSource,
            source,
            source8.value ?: return@addSource,
            source9.value ?: return@addSource
        )
    }

    addSource(source8) { source ->
        value = merger(
            source1.value ?: return@addSource,
            source2.value ?: return@addSource,
            source3.value ?: return@addSource,
            source4.value ?: return@addSource,
            source5.value ?: return@addSource,
            source6.value ?: return@addSource,
            source7.value ?: return@addSource,
            source,
            source9.value ?: return@addSource
        )
    }

    addSource(source9) { source ->
        value = merger(
            source1.value ?: return@addSource,
            source2.value ?: return@addSource,
            source3.value ?: return@addSource,
            source4.value ?: return@addSource,
            source5.value ?: return@addSource,
            source6.value ?: return@addSource,
            source7.value ?: return@addSource,
            source8.value ?: return@addSource,
            source
        )
    }
}