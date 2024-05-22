package ke.co.banit.instagramprofile

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import ke.co.banit.instagramprofile.data.People
import ke.co.banit.instagramprofile.data.ProfileStat

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Column {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState(), true)
            ) {
                TopBar()
                ProfileStats()
                ProfileContent()
                Spacer(modifier = Modifier.size(6.dp))
                ProfileButton()
                DiscoverPeople()
                Spacer(modifier = Modifier.size(12.dp))
                Posts()
            }
            PostContent()
        }
    }
}

@Composable
fun Posts() {
    Column{
        val postTypeIcons = listOf(
            painterResource(id = R.drawable.ic_grid),
            painterResource(id = R.drawable.ic_video),
            painterResource(id = R.drawable.ic_photo)
        )
        PostTabLayout(postTypeIcons)
        //
    }
}

@Composable
fun PostItem(post: Painter) {
    Image(
        painter = post, contentDescription = "Post",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(2.5.dp)
            .clip(RoundedCornerShape(4.dp))
    )
}

@Composable
fun PostContent() {
    val posts = listOf(
        painterResource(id = R.drawable.profile_image),
        painterResource(id = R.drawable.profile_image),
        painterResource(id = R.drawable.profile_image),
        painterResource(id = R.drawable.profile_image),
        painterResource(id = R.drawable.profile_image),
        painterResource(id = R.drawable.profile_image),
        painterResource(id = R.drawable.profile_image),
        painterResource(id = R.drawable.profile_image),
        painterResource(id = R.drawable.profile_image)
    )
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .padding(12.dp)
            .fillMaxHeight(),
    ) {
        items(posts.size) {
            PostItem(post = posts[it])
        }
    }
}

@Composable
fun PostTabLayout(postTypeIcons: List<Painter>) {
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        postTypeIcons.forEachIndexed { index, painter ->
            Box(contentAlignment = Alignment.Center, modifier = Modifier
                .weight(1f)
                .clickable {
                    selectedTabIndex = index
                }
                .drawBehind {
                    if (selectedTabIndex == index) {
                        val strokeWidth = 1.dp.toPx()
                        val y = (size.height + 15) - strokeWidth / 2
                        drawLine(
                            color = Color.Gray,
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = strokeWidth
                        )
                    }
                }
            ) {
                Icon(
                    painter = painter,
                    contentDescription = "Post Type",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun DiscoverPeople(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(12.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Discover People",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "See All",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
        }
        Spacer(modifier = Modifier.size(12.dp))
        val people = listOf(
            People(
                name = "Cute Cat",
                profilePicture = R.drawable.profile_image,
                connection = "Suggested for you"
            ),
            People(
                name = "Cute Cat",
                profilePicture = R.drawable.profile_image,
                connection = "Suggested for you"
            ),
            People(
                name = "Cute Cat",
                profilePicture = R.drawable.profile_image,
                connection = "Followed by bantos ben"
            ),
            People(
                name = "Cute Cat",
                profilePicture = R.drawable.profile_image,
                connection = "Suggested for you"
            )
        )

        LazyRow {
            item(people.size) {
                people.forEach {
                    DiscoverPeopleItem(person = it)
                }
            }
        }

    }
}

@Composable
fun DiscoverPeopleItem(modifier: Modifier = Modifier, person: People) {
    Box(
        modifier = modifier
            .width(200.dp)
            .padding(3.dp)
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.size(12.dp))
            Image(
                painter = painterResource(id = person.profilePicture),
                contentDescription = person.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape),
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = person.name, style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = person.connection, style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.size(12.dp))
            Button(modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp), onClick = {}) {
                Text(text = "Follow")
            }
        }
    }
}


@Composable
fun ProfileButton() {
    val constraints = ConstraintSet {
        val btnEdit = createRefFor("btnEdit")
        val btnShare = createRefFor("btnShare")
        val btnAdd = createRefFor("btnAdd")

        constrain(btnEdit) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(btnShare.start, margin = 2.dp)
            width = Dimension.fillToConstraints
            height = Dimension.wrapContent
        }
        constrain(btnShare) {
            top.linkTo(parent.top)
            start.linkTo(btnEdit.end, margin = 2.dp)
            end.linkTo(btnAdd.start, margin = 2.dp)
            width = Dimension.fillToConstraints
            height = Dimension.wrapContent
        }
        constrain(btnAdd) {
            top.linkTo(parent.top)
            start.linkTo(btnShare.end, margin = 2.dp)
            end.linkTo(parent.end)
            width = Dimension.wrapContent
            height = Dimension.wrapContent
        }
    }
    ConstraintLayout(
        constraintSet = constraints,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
    ) {
        Button(
            modifier = Modifier.layoutId("btnEdit"),
            onClick = {},
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Edit Profile")
        }
        Button(
            modifier = Modifier.layoutId("btnShare"),
            onClick = {},
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Share Profile")
        }
        Button(
            modifier = Modifier.layoutId("btnAdd"),
            onClick = {},
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add_user),
                contentDescription = "Add user"
            )
        }
    }
}

@Composable
fun ProfileContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(12.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Angatia Benson",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = "Android Developer",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.size(6.dp))
        Text(
            text = "Freelance Android Developer. I love to learn new things.",
            style = MaterialTheme.typography.bodySmall,
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = "https://banit.co.ke/",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Blue,
            textDecoration = TextDecoration.Underline
        )
    }
}

@Composable
fun ProfileStats(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(3.dp)
                .clip(RoundedCornerShape(12.dp))
        ) {
            Image(
                painter = painterResource(R.drawable.profile_image),
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(85.dp)
                    .clip(CircleShape)
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileStats(stats = ProfileStat("Posts", "150"))
            ProfileStats(stats = ProfileStat("Followers", "100k"))
            ProfileStats(stats = ProfileStat("Following", "50"))
        }
    }
}

@Composable
fun ProfileStats(modifier: Modifier = Modifier, stats: ProfileStat) {
    Column(
        modifier = modifier
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stats.stat,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = stats.title,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun TopBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
            .padding(top = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "angatiabenson",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_drop_down),
                contentDescription = "Verified",
                modifier = Modifier.size(20.dp)
            )
        }
        Row(
            modifier = Modifier.width(100.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Icon(
                painter = painterResource(id = R.drawable.threads),
                contentDescription = "Threads",
                modifier = Modifier.size(22.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "Add",
                modifier = Modifier.size(22.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = "Menu",
                modifier = Modifier.size(22.dp)
            )
        }
    }
}