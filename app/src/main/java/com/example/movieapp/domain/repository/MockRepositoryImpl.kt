package com.example.movieapp.domain.repository

import android.os.Handler
import android.os.Looper
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.MovieCategory
import java.util.concurrent.ExecutorService
import kotlin.random.Random

@Deprecated("this repository just for test without internet")
class MockRepositoryImpl : Repository {
    private val handler: Handler = Handler(Looper.getMainLooper())
    override fun getMovies(
        executor: ExecutorService,
        callback: (result: RepositoryResult<List<MovieCategory>>) -> Unit
    ) {
        executor.execute {
            val isOk = Random.nextBoolean()
            if (isOk) {
                val result = listOf<MovieCategory>(
                    MovieCategory(
                        "Реомендации", listOf<Movie>(
                            Movie(
                                10,
                                "довод",
                                "После теракта в киевском оперном театре агент ЦРУ объединяется с британской разведкой, чтобы противостоять русскому олигарху, который сколотил состояние на торговле оружием.",
                                "123",
                                "10",
                                3f,
                            ),
                            Movie(
                                10,
                                "довод",
                                "После теракта в киевском оперном театре агент ЦРУ объединяется с британской разведкой, чтобы противостоять русскому олигарху, который сколотил состояние на торговле оружием.",
                                "123",
                                "10",
                                3f,
                            ),
                            Movie(
                                10,
                                "довод",
                                "После теракта в киевском оперном театре агент ЦРУ объединяется с британской разведкой, чтобы противостоять русскому олигарху, который сколотил состояние на торговле оружием.",
                                "123",
                                "10",
                                3f,
                            ),
                            Movie(
                                10,
                                "довод",
                                "После теракта в киевском оперном театре агент ЦРУ объединяется с британской разведкой, чтобы противостоять русскому олигарху, который сколотил состояние на торговле оружием.",
                                "123",
                                "10",
                                3f,
                            )
                        )
                    ), MovieCategory(
                        "Популярное", listOf<Movie>(
                            Movie(
                                10,
                                "довод",
                                "После теракта в киевском оперном театре агент ЦРУ объединяется с британской разведкой, чтобы противостоять русскому олигарху, который сколотил состояние на торговле оружием.",
                                "123",
                                "10",
                                3f,
                            ),
                            Movie(
                                10,
                                "довод",
                                "После теракта в киевском оперном театре агент ЦРУ объединяется с британской разведкой, чтобы противостоять русскому олигарху, который сколотил состояние на торговле оружием.",
                                "123",
                                "10",
                                3f,
                            ),
                            Movie(
                                10,
                                "довод",
                                "После теракта в киевском оперном театре агент ЦРУ объединяется с британской разведкой, чтобы противостоять русскому олигарху, который сколотил состояние на торговле оружием.",
                                "123",
                                "10",
                                3f,
                            ),
                            Movie(
                                10,
                                "довод",
                                "После теракта в киевском оперном театре агент ЦРУ объединяется с британской разведкой, чтобы противостоять русскому олигарху, который сколотил состояние на торговле оружием.",
                                "123",
                                "10",
                                3f,
                            )
                        )
                    ), MovieCategory(
                        "Новинки", listOf(
                            Movie(
                                10,
                                "довод",
                                "После теракта в киевском оперном театре агент ЦРУ объединяется с британской разведкой, чтобы противостоять русскому олигарху, который сколотил состояние на торговле оружием.",
                                "123",
                                "10",
                                3f,
                            ),
                            Movie(
                                10,
                                "довод",
                                "После теракта в киевском оперном театре агент ЦРУ объединяется с британской разведкой, чтобы противостоять русскому олигарху, который сколотил состояние на торговле оружием.",
                                "123",
                                "10",
                                3f,
                            ),
                            Movie(
                                10,
                                "довод",
                                "После теракта в киевском оперном театре агент ЦРУ объединяется с британской разведкой, чтобы противостоять русскому олигарху, который сколотил состояние на торговле оружием.",
                                "123",
                                "10",
                                3f,
                            ),
                            Movie(
                                10,
                                "довод",
                                "После теракта в киевском оперном театре агент ЦРУ объединяется с британской разведкой, чтобы противостоять русскому олигарху, который сколотил состояние на торговле оружием.",
                                "123",
                                "10",
                                3f,
                            ),
                        )
                    )
                )
                handler.post { callback(Success(result)) }
            } else {
                handler.post { callback(Error(RuntimeException())) }
            }
        }

    }

    override suspend fun getMovies(): RepositoryResult<List<MovieCategory>> {
        return Success(listOf())
    }
}
