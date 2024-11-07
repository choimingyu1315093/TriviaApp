Retrofit-Json, Clean Architecture, DataOrException, Hilt, ViewModel, Repository

![스크린샷 2024-11-06 오전 7 58 23](https://github.com/user-attachments/assets/c44c5210-0229-4b26-a6a6-d0ff301a4f5b)

Text(
        text = buildAnnotatedString {
            withStyle(
                style = ParagraphStyle(
                    textIndent = TextIndent.None
                )
            ){
                withStyle(
                    style = SpanStyle(
                        color = AppColors.mLightGray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 27.sp
                    )
                ){
                    append("Question $counter/")
                }
                withStyle(
                    style = SpanStyle(
                        color = AppColors.mLightGray,
                        fontWeight = FontWeight.Light,
                        fontSize = 14.sp
                    )
                ){
                    append("$outOf")
                }
            }
        },
        modifier = modifier.padding(20.dp)
    )
