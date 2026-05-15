package com.nammaskill.app.data

object FakeData {

    val trades = listOf(
        "Electrician",
        "Welding",
        "Sewing",
        "Mobile Repair",
        "Coding",
    )

    val centers = listOf(
        SkillCenter(
            id = "c1",
            name = "Govt Skill Center - Taluk HQ",
            address = "Near Bus Stand, Taluk HQ",
            phone = "+919900001111",
            lat = 12.9716,
            lng = 77.5946,
        ),
        SkillCenter(
            id = "c2",
            name = "Govt ITI - District",
            address = "ITI Road, District City",
            phone = "+919900002222",
            lat = 12.2958,
            lng = 76.6394,
        ),
        SkillCenter(
            id = "c3",
            name = "Women Skill Hub",
            address = "Community Hall, Market Area",
            phone = "+919900003333",
            lat = 13.0827,
            lng = 80.2707,
        ),
    )

    // Bigger demo dataset so the Courses screen looks realistic even without Firebase.
    // Dates use ISO format so string sorting works for "upcoming".
    val courses = listOf(
        // Electrician
        CourseBatch("b1", "Electrician", "c2", "2026-06-10", 3, DurationType.SHORT_TERM, "10th pass • Age 18-30", true),
        CourseBatch("b2", "Electrician", "c1", "2026-06-25", 3, DurationType.SHORT_TERM, "10th pass • Age 18-35", false),
        CourseBatch("b3", "Electrician", "c2", "2026-07-15", 6, DurationType.LONG_TERM, "10th pass • Age 18-30", true),
        CourseBatch("b4", "Electrician", "c3", "2026-08-01", 6, DurationType.LONG_TERM, "10th pass • Age 18+", false),

        // Welding
        CourseBatch("b5", "Welding", "c1", "2026-06-20", 3, DurationType.SHORT_TERM, "8th pass • Age 18-35", true),
        CourseBatch("b6", "Welding", "c2", "2026-07-08", 3, DurationType.SHORT_TERM, "8th pass • Age 18-30", true),
        CourseBatch("b7", "Welding", "c1", "2026-08-18", 6, DurationType.LONG_TERM, "10th pass • Age 18-35", true),
        CourseBatch("b8", "Welding", "c2", "2026-09-05", 6, DurationType.LONG_TERM, "10th pass • Medical fitness", false),

        // Sewing
        CourseBatch("b9", "Sewing", "c3", "2026-06-15", 2, DurationType.SHORT_TERM, "Any education • Age 18+", false),
        CourseBatch("b10", "Sewing", "c3", "2026-07-10", 3, DurationType.SHORT_TERM, "Any education • Age 18+", true),
        CourseBatch("b11", "Sewing", "c1", "2026-08-12", 6, DurationType.LONG_TERM, "Any education • Age 18+", false),
        CourseBatch("b12", "Sewing", "c3", "2026-09-20", 6, DurationType.LONG_TERM, "Any education • Interest in tailoring", true),

        // Mobile Repair
        CourseBatch("b13", "Mobile Repair", "c1", "2026-07-01", 6, DurationType.LONG_TERM, "10th pass • Age 18-30", true),
        CourseBatch("b14", "Mobile Repair", "c2", "2026-07-22", 3, DurationType.SHORT_TERM, "10th pass • Basic math", false),
        CourseBatch("b15", "Mobile Repair", "c1", "2026-08-28", 3, DurationType.SHORT_TERM, "10th pass • Age 18-35", true),
        CourseBatch("b16", "Mobile Repair", "c2", "2026-10-05", 6, DurationType.LONG_TERM, "10th pass • Interest in electronics", true),

        // Coding
        CourseBatch("b17", "Coding", "c2", "2026-07-05", 6, DurationType.LONG_TERM, "12th pass • Basic English", false),
        CourseBatch("b18", "Coding", "c2", "2026-06-30", 3, DurationType.SHORT_TERM, "12th pass • Basic typing", true),
        CourseBatch("b19", "Coding", "c1", "2026-08-05", 3, DurationType.SHORT_TERM, "12th pass • Willingness to learn", false),
        CourseBatch("b20", "Coding", "c2", "2026-09-12", 6, DurationType.LONG_TERM, "12th pass • Logical thinking", true),

        // More variety (repeat trades with different centers/dates)
        CourseBatch("b21", "Electrician", "c1", "2026-09-01", 3, DurationType.SHORT_TERM, "10th pass • Age 18-30", true),
        CourseBatch("b22", "Welding", "c1", "2026-10-10", 3, DurationType.SHORT_TERM, "8th pass • Age 18-35", true),
        CourseBatch("b23", "Sewing", "c3", "2026-10-15", 2, DurationType.SHORT_TERM, "Any education • Age 18+", false),
        CourseBatch("b24", "Mobile Repair", "c2", "2026-09-25", 3, DurationType.SHORT_TERM, "10th pass • Basic electronics", true),
        CourseBatch("b25", "Coding", "c2", "2026-10-20", 3, DurationType.SHORT_TERM, "12th pass • Basic English", true),
    )

    val stories = listOf(
        SuccessStory(
            id = "s1",
            title = "Asha got placed as a Welder",
            body = "After a 3‑month welding batch, Asha joined a fabrication unit. She now earns steadily and supports her family."
        ),
        SuccessStory(
            id = "s2",
            title = "Ravi started a Mobile Repair shop",
            body = "Ravi completed mobile repair training and opened a small shop near the weekly market. His income doubled within 6 months."
        ),
        SuccessStory(
            id = "s3",
            title = "Meena learned Sewing and got orders",
            body = "Meena learned stitching and tailoring. She now takes blouse and uniform orders from nearby villages."
        ),
    )
}
