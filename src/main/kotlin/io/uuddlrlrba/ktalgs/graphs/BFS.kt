/*
 * Copyright (c) 2017 Kotlin Algorithm Club
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.uuddlrlrba.ktalgs.graphs

import io.uuddlrlrba.ktalgs.datastructures.Queue

class BFS {
    companion object Implementations {
        fun iterative(
            graph: Graph,
            levelOrder: ((Int) -> Unit)? = null,
        ) {
            val visited = BooleanArray(graph.V)
            val queue = Queue<Int>()
            for (i in 0 until graph.V) {
                if (!visited[i]) {
                    queue.add(i)
                    visited[i] = true
                    levelOrder?.invoke(i)
                    while (!queue.isEmpty()) {
                        val v = queue.poll()
                        for (w in graph.adjacentVertices(v)) {
                            if (!visited[w]) {
                                queue.add(w)
                                visited[w] = true
                                levelOrder?.invoke(i)
                            }
                        }
                    }
                }
            }
        }
    }
}
