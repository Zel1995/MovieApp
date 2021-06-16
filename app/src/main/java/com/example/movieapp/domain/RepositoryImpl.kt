package com.example.movieapp.domain

import android.os.Handler
import android.os.Looper
import com.example.movieapp.R
import java.util.concurrent.ExecutorService
import kotlin.random.Random

class RepositoryImpl : Repository {
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
                                "Довод",
                                "После теракта в киевском оперном театре агент ЦРУ объединяется с британской разведкой, чтобы противостоять русскому олигарху, который сколотил состояние на торговле оружием.",
                                R.drawable.dovod
                            ),
                            Movie(
                                "Начало",
                                "Кобб – талантливый вор, лучший из лучших в опасном искусстве извлечения: он крадет ценные секреты из глубин подсознания во время сна, когда человеческий разум наиболее уязвим. ",
                                R.drawable.nachal
                            ),
                            Movie(
                                "Престиж",
                                "Роберт и Альфред - фокусники-иллюзионисты, которые на рубеже XIX и XX веков соперничали друг с другом в Лондоне.",
                                R.drawable.prestij
                            ),
                            Movie(
                                "Иллюзия обмана",
                                "Команда лучших иллюзионистов мира проворачивает дерзкие ограбления прямо во время своих шоу, играя в кошки-мышки с агентами ФБР.",
                                R.drawable.illusobmana
                            )
                        )
                    ), MovieCategory(
                        "Популярное", listOf<Movie>(
                            Movie(
                                "Престиж",
                                "Роберт и Альфред - фокусники-иллюзионисты, которые на рубеже XIX и XX веков соперничали друг с другом в Лондоне.",
                                R.drawable.prestij
                            ),
                            Movie(
                                "Довод",
                                "После теракта в киевском оперном театре агент ЦРУ объединяется с британской разведкой, чтобы противостоять русскому олигарху, который сколотил состояние на торговле оружием.",
                                R.drawable.dovod
                            ),
                            Movie(
                                "Начало",
                                "Кобб – талантливый вор, лучший из лучших в опасном искусстве извлечения: он крадет ценные секреты из глубин подсознания во время сна, когда человеческий разум наиболее уязвим. ",
                                R.drawable.nachal
                            ),
                            Movie(
                                "Иллюзия обмана",
                                "Команда лучших иллюзионистов мира проворачивает дерзкие ограбления прямо во время своих шоу, играя в кошки-мышки с агентами ФБР.",
                                R.drawable.illusobmana
                            )
                        )
                    ), MovieCategory(
                        "Новинки", listOf(
                            Movie(
                                "Иллюзия обмана",
                                "Команда лучших иллюзионистов мира проворачивает дерзкие ограбления прямо во время своих шоу, играя в кошки-мышки с агентами ФБР.",
                                R.drawable.illusobmana
                            ),
                            Movie(
                                "Довод",
                                "После теракта в киевском оперном театре агент ЦРУ объединяется с британской разведкой, чтобы противостоять русскому олигарху, который сколотил состояние на торговле оружием.",
                                R.drawable.dovod
                            ),
                            Movie(
                                "Начало",
                                "Кобб – талантливый вор, лучший из лучших в опасном искусстве извлечения: он крадет ценные секреты из глубин подсознания во время сна, когда человеческий разум наиболее уязвим. ",
                                R.drawable.nachal
                            ),
                            Movie(
                                "Престиж",
                                "Роберт и Альфред - фокусники-иллюзионисты, которые на рубеже XIX и XX веков соперничали друг с другом в Лондоне.",
                                R.drawable.prestij
                            ),
                        )
                    )
                )
                handler.post { callback(Success(result)) }
            } else {
                handler.post { callback(Error(RuntimeException())) }
                println(1)
            }
        }

    }
}

sealed class RepositoryResult<T>
data class Success<T>(val value: T) : RepositoryResult<T>()
data class Error<T>(val value: Throwable) : RepositoryResult<T>()