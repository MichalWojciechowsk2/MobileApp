package pl.wsei.pam.lab06.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.wsei.pam.lab06.TodoTask
import androidx.compose.material3.MaterialTheme


@Composable
fun ListItem(item: TodoTask, modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(120.dp)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = item.title, style = MaterialTheme.typography.bodyLarge)

            Text(text = "Deadline: ${item.deadline}", style = MaterialTheme.typography.bodySmall)

            Text(text = "Priority: ${item.priority}", style = MaterialTheme.typography.bodySmall)

            Text(
                text = if (item.isDone) "Status: Done" else "Status: Not Done",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
