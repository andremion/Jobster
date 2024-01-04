package io.github.andremion.jobster.ui.jobpostingsearch

//@Preview
//@Composable
//private fun ScreenContentSearchBarActivePreview() {
//    saveablePresenter {  }
//    ScreenContent(
//        uiState = JobPostingSearchUiState.Initial.copy(
//            isSearchBarActive = true
//        ),
//        onUiEvent = {}
//    )
//}
//
//@Preview
//@Composable
//private fun ScreenContentLoadingPreview() {
//    ScreenContent(
//        uiState = JobPostingSearchUiState.Initial.copy(
//            isSearchBarActive = true,
//            isLoading = true
//        ),
//        onUiEvent = {}
//    )
//}
//
//@Preview
//@Composable
//private fun ScreenContentGeneralErrorPreview() {
//    ScreenContent(
//        uiState = JobPostingSearchUiState.Initial.copy(
//            isSearchBarActive = true,
//            error = JobRepository.GeneralJobPostingSearchException(
//                cause = RuntimeException()
//            )
//        ),
//        onUiEvent = {}
//    )
//}
//
//@Preview
//@Composable
//private fun ScreenContentSearchErrorPreview() {
//    ScreenContent(
//        uiState = JobPostingSearchUiState.Initial.copy(
//            isSearchBarActive = true,
//            error = JobRepository.JobPostingSearchException(
//                message = "Something went wrong!",
//                cause = RuntimeException()
//            )
//        ),
//        onUiEvent = {}
//    )
//}
//
//@Preview
//@Composable
//private fun ScreenContentResultPreview() {
//    ScreenContent(
//        uiState = JobPostingSearchUiState.Initial.copy(
//            url = "https://careers.google.com/jobs/results/134631828608854950-software-engineer-android/?company=Google&company=YouTube&employment_type=FULL_TIME&hl=en_US&jlo=en_US&q=android&sort_by=relevance",
//            jobPosting = JobPostingSearchUiState.JobPosting(
//                role = "Software Engineer - Android",
//                company = "Google",
//                logo = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png",
//                contents = listOf(
//                    JobPostingSearchUiState.JobPosting.Content(
//                        title = "How to prepare for a Google interview",
//                        description = "A guide to help you prepare for the technical aspects of Google interviews",
//                        url = "https://www.freecodecamp.org/news/how-to-prepare-for-a-google-software-engineer-interview-6d3c1cd3eae7/",
//                        image = "https://www.freecodecamp.org/news/content/images/2021/03/How-to-prepare-for-a-Google-interview.png",
//                        isChecked = true,
//                    ),
//                    JobPostingSearchUiState.JobPosting.Content(
//                        title = "How to be a great software engineer",
//                        description = "A guide to help you prepare for the non-technical aspects of Google interviews",
//                        url = "https://www.freecodecamp.org/news/how-to-be-a-great-software-engineer-3f5d44bc6f3e/",
//                        image = "https://www.freecodecamp.org/news/content/images/2021/03/How-to-be-a-great-software-engineer.png",
//                        isChecked = false,
//                    ),
//                )
//            ),
//        ),
//        onUiEvent = {}
//    )
//}
