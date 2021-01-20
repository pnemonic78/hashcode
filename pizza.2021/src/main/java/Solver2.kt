import java.io.File
import java.io.IOException
import java.util.*

class Solver2 {

    @Throws(IOException::class)
    fun solve(fileIn: File): Solution {
        val parser = ProblemParser()
        val problem = parser.parse(fileIn)
        return solve(problem)
    }

    fun solve(problem: Problem): Solution {
        val solution = Solution(problem)
        val pizzas = ArrayList(problem.pizzas)
        if (pizzas.isEmpty()) {
            return solution
        }
        pizzas.sortWith(PizzaSorter())
        val teams = solution.teams

        val delivered2 = solve(pizzas, 2, problem.numberOf2PersonTeams)
        if (delivered2 != null) {
            teams.addAll(delivered2)
        }
        val delivered3 = solve(pizzas, 3, problem.numberOf3PersonTeams)
        if (delivered3 != null) {
            teams.addAll(delivered3)
        }
        val delivered4 = solve(pizzas, 4, problem.numberOf4PersonTeams)
        if (delivered4 != null) {
            teams.addAll(delivered4)
        }

        return solution
    }

    private fun solve(
        pizzas: MutableList<Pizza>,
        numberOfTeamMembers: Int,
        numberOfTeams: Int
    ): List<Team>? {
        if (pizzas.isEmpty()) return null
        val teams = ArrayList<Team>()
        for (t in 0 until numberOfTeams) {
            val team = Team(numberOfTeamMembers)
            for (m in 0 until numberOfTeamMembers) {
                if (pizzas.isEmpty()) return teams
                val pizza = pizzas.removeAt(0)
                team.pizzas.add(pizza)
            }
            teams.add(team)
        }
        return teams
    }

    /** More toppings first. */
    inner class PizzaSorter : Comparator<Pizza> {
        override fun compare(o1: Pizza, o2: Pizza): Int {
            return -o1.toppings.size.compareTo(o2.toppings.size)
        }
    }
}